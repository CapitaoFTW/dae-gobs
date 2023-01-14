package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import org.apache.commons.lang3.tuple.Pair;
import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.NewOcorrenciaMensagemDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.OcorrenciaMensagem;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsBadRequestException;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.stream.Collectors;


@Stateless
public class OcorrenciaBean {
    @EJB
    private ApoliceBean apoliceBean;
    @EJB
    private ClienteBean clienteBean;
    @EJB
    private EmailBean emailBean;

    @PersistenceContext
    private EntityManager entityManager;

    public Pair<Ocorrencia, OcorrenciaMensagem> create(Integer clienteId, Integer apoliceId, String assunto, NewOcorrenciaMensagemDTO mensagemDTO) {
        if (apoliceBean.getApolice(apoliceId) == null)
            throw new GobsEntityNotFoundException(apoliceId, "Falha ao registar ocorrencia, apólice não existe.");

        Ocorrencia ocorrencia = new Ocorrencia(clienteId, apoliceId, assunto, EstadoOcorrencia.Criada);
        try {
            entityManager.persist(ocorrencia);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }

        return addMessageInternal(ocorrencia, mensagemDTO, false);
    }

    public Pair<Ocorrencia, OcorrenciaMensagem> addMessage(Integer ocorrenciaId, NewOcorrenciaMensagemDTO mensagemDTO, Integer estado, boolean notificar) {
        Ocorrencia ocorrencia = find(ocorrenciaId);
        if (ocorrencia == null)
            throw new GobsEntityNotFoundException(ocorrenciaId, "Falha ao obter Ocorrência, Ocorrência não existe");

        if (estado != null) {
            updateEstadoInternal(ocorrencia, EstadoOcorrencia.fromValue(estado), false);
        }

        return addMessageInternal(ocorrencia, mensagemDTO, notificar);
    }

    private Pair<Ocorrencia, OcorrenciaMensagem> addMessageInternal(Ocorrencia ocorrencia, NewOcorrenciaMensagemDTO mensagemDTO, boolean notificar) {
        OcorrenciaMensagem mensagem = mensagemDTO.toEntity();
        mensagem.setOcorrencia(ocorrencia);

        try {
            entityManager.persist(mensagem);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }

        ocorrencia.addMensagem(mensagem);

        if (notificar) {
            notificarCliente(ocorrencia.getId(), ocorrencia.getApoliceId(), ocorrencia.getClienteId(), String.format("Você recebeu uma nova mensagem na sua ocorrência.\nEstado atual: %s\nMensagem: %s", ocorrencia.getEstadoOcorrencia(), mensagem.getMensagem()));
        }

        return Pair.of(ocorrencia, mensagem);
    }

    public Ocorrencia find(Integer id) {
        return find(id, LockModeType.OPTIMISTIC);
    }

    public Ocorrencia find(Integer id, LockModeType lockModeType) {
        return entityManager.find(Ocorrencia.class, id, lockModeType);
    }

    public Ocorrencia updateEstado(Integer ocorrenciaId, EstadoOcorrencia novoEstado, boolean isCliente) {
        Ocorrencia ocorrencia = find(ocorrenciaId);
        if (ocorrencia == null)
            throw new GobsEntityNotFoundException(ocorrenciaId, "Falha ao obter Ocorrência, Ocorrência não existe");

        if (ocorrencia.getEstadoOcorrencia() == novoEstado)
            throw new GobsBadRequestException(novoEstado, "Falha ao atualizar estado, o novo estadoé igual ao antigo");

        if (isCliente) {
            switch (novoEstado) {
                case Criada:
                case AguardarMaisDados:
                case ParaReparacao:
                case Invalida:
                    throw new GobsBadRequestException(novoEstado, "Falha ao atualizar estado, o novo estado é inválido");
            }
        } else {
            switch (novoEstado) {
                case Criada:
                case ParaAnalise:
                case EmReparacao:
                case ImpossivelReparar:
                case Reparado:
                    throw new GobsBadRequestException(novoEstado, "Falha ao atualizar estado, o novo estado é inválido");
            }
        }

        return updateEstadoInternal(ocorrencia, novoEstado, !isCliente);
    }


    private Ocorrencia updateEstadoInternal(Ocorrencia ocorrencia, EstadoOcorrencia novoEstado, boolean notificar) {
        EstadoOcorrencia estado = ocorrencia.getEstadoOcorrencia();
        if (novoEstado == estado)
            throw new GobsBadRequestException(novoEstado, "Falha ao atualizar estado, o novo estadoé igual ao antigo");

        switch (estado) {
            case Concluida:
            case Invalida:
                throw new GobsBadRequestException(estado, "A ocorrência está marcada como concluida ou inválida");
        }

        try {
            ocorrencia.setEstadoOcorrencia(novoEstado);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }

        if (notificar) {
            notificarCliente(ocorrencia.getId(), ocorrencia.getApoliceId(), ocorrencia.getClienteId(), String.format("Os estado da sua ocorrência foi alterado: %s", estado));
        }

        return ocorrencia;
    }

    private void notificarCliente(Integer ocorrenciaId, Integer apoliceId, Integer clienteId, String mensagem) {
        Cliente cliente = clienteBean.getCliente(clienteId);
        if (cliente == null)
            return;

        Apolice apolice = apoliceBean.getApolice(apoliceId);
        if (apolice == null)
            return;

        EmailDTO emailDTO = new EmailDTO(String.format("Ocorrência: %d | Bem: %s", ocorrenciaId, apolice.getBem()), mensagem);
        emailBean.send(cliente.getEmail(), emailDTO);
    }

    public boolean exists(Integer id) {
        return entityManager.createNamedQuery("existsOcorrencia", Long.class).setParameter("id", id).getSingleResult() > 0;
    }

    public Collection<Ocorrencia> findByCliente(Integer id) {
        return entityManager
                .createNamedQuery("getOcorrenciasByCliente", Ocorrencia.class)
                .setParameter("clienteId", id)
                .getResultStream()
                .collect(Collectors.toList());
    }

    public Collection<Ocorrencia> getOcorrencias() {
        return entityManager
                .createNamedQuery("getOcorrencias", Ocorrencia.class)
                .getResultStream()
                .collect(Collectors.toList());
    }

    public Collection<Ocorrencia> getOcorrenciasRecentes(Integer limite) {
        return entityManager
                .createNamedQuery("getOcorrenciasRecentes", Ocorrencia.class)
                .setMaxResults(limite)
                .getResultStream()
                .collect(Collectors.toList());
    }

    public Collection<Ocorrencia> findByClienteRecente(Integer id, Integer limite) {
        return entityManager
                .createNamedQuery("getOcorrenciaByClienteRecente", Ocorrencia.class)
                .setParameter("clienteId", id)
                .setMaxResults(limite)
                .getResultStream()
                .collect(Collectors.toList());
    }

    public Integer getOcorrenciaOwner(Integer ocorrenciaId) {
        if (!exists(ocorrenciaId))
            throw new GobsEntityNotFoundException(ocorrenciaId, "Falha ao obter Ocorrência, Ocorrência não existe");

        return entityManager
                .createNamedQuery("getOcorrenciaOwner", Integer.class)
                .setParameter("id", ocorrenciaId)
                .getSingleResult();
    }
}