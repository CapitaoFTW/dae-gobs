package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import org.apache.commons.lang3.tuple.Pair;
import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.NewOcorrenciaMensagemDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.OcorrenciaMensagem;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Collection;

import java.util.Objects;
import java.util.logging.Logger;
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

        OcorrenciaMensagem mensagem = addMessage(ocorrencia, mensagemDTO);
        return Pair.of(ocorrencia, mensagem);
    }

    public OcorrenciaMensagem addMessage(Ocorrencia ocorrencia, NewOcorrenciaMensagemDTO mensagemDTO) {
        OcorrenciaMensagem mensagem = mensagemDTO.toEntity();
        mensagem.setOcorrencia(ocorrencia);

        try {
            entityManager.persist(mensagem);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }

        return mensagem;
    }

    /*public void update(Integer clienteId, Integer apoliceId, String assunto, String descricao) {
    }*/

    public boolean exists(Integer id) {
        return entityManager.createNamedQuery("existsOcorrencia", Long.class).setParameter("id", id).getSingleResult() > 0;
    }

    public Ocorrencia find(Integer id) {
        return find(id, LockModeType.OPTIMISTIC);
    }

    public Ocorrencia find(Integer id, LockModeType lockModeType) {
        return entityManager.find(Ocorrencia.class, id, lockModeType);
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
}