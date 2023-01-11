package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
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
    private ClienteBean clienteBean;
    @EJB
    private ApoliceBean apoliceBean;
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Ocorrencia ocorrencia) {
        Integer clienteId = ocorrencia.getClienteId();
        if (clienteBean.getCliente(clienteId) == null)
            throw new GobsEntityNotFoundException(clienteId, "Falha ao registar ocorrencia, cliente não existe.");

        Integer apoliceId = ocorrencia.getApoliceId();
        if (apoliceBean.getApolice(apoliceId) == null)
            throw new GobsEntityNotFoundException(apoliceId, "Falha ao registar ocorrencia, apólice não existe.");

        try {
            entityManager.persist(ocorrencia);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }
    }

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

    public Collection<Ocorrencia> findByClienteRecente(Integer id, Integer limite) {
        return entityManager
                .createNamedQuery("getOcorrenciaByClienteRecente", Ocorrencia.class)
                .setParameter("clienteId", id)
                .setMaxResults(limite)
                .getResultStream()
                .collect(Collectors.toList());
    }
}