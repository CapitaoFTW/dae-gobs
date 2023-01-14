package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import org.apache.commons.lang3.tuple.Pair;
import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.NewOcorrenciaMensagemDTO;
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

        return addMessageInternal(ocorrencia, mensagemDTO);
    }

    public Pair<Ocorrencia, OcorrenciaMensagem> addMessage(Integer ocorrenciaId, NewOcorrenciaMensagemDTO mensagemDTO, Integer estado) {
        Ocorrencia ocorrencia = find(ocorrenciaId);
        if (ocorrencia == null)
            throw new GobsEntityNotFoundException(ocorrenciaId, "Falha ao obter Ocorrência, Ocorrência não existe");

        updateEstadoInternal(ocorrencia, EstadoOcorrencia.fromValue(estado));
        return addMessageInternal(ocorrencia, mensagemDTO);
    }

    private Pair<Ocorrencia, OcorrenciaMensagem> addMessageInternal(Ocorrencia ocorrencia, NewOcorrenciaMensagemDTO mensagemDTO) {
        EstadoOcorrencia estado = ocorrencia.getEstadoOcorrencia();
        switch (estado) {
            case Concluida:
            case Invalida:
                throw new GobsBadRequestException(estado, "A ocorrência está marcada como concluida ou inválida");
        }

        OcorrenciaMensagem mensagem = mensagemDTO.toEntity();
        mensagem.setOcorrencia(ocorrencia);

        try {
            entityManager.persist(mensagem);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }

        ocorrencia.addMensagem(mensagem);
        return Pair.of(ocorrencia, mensagem);
    }

    public Ocorrencia find(Integer id) {
        return find(id, LockModeType.OPTIMISTIC);
    }

    public Ocorrencia find(Integer id, LockModeType lockModeType) {
        return entityManager.find(Ocorrencia.class, id, lockModeType);
    }

    public Ocorrencia updateEstado(Integer ocorrenciaId, EstadoOcorrencia novoEstado) {
        Ocorrencia ocorrencia = find(ocorrenciaId);
        if (ocorrencia == null)
            throw new GobsEntityNotFoundException(ocorrenciaId, "Falha ao obter Ocorrência, Ocorrência não existe");

        return updateEstadoInternal(ocorrencia, novoEstado);
    }

    private Ocorrencia updateEstadoInternal(Ocorrencia ocorrencia, EstadoOcorrencia novoEstado) {
        EstadoOcorrencia estado = ocorrencia.getEstadoOcorrencia();
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

        return ocorrencia;
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