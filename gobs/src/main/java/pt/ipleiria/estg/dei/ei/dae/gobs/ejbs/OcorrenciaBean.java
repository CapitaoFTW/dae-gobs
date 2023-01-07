package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.stream.Collectors;

@Stateless
public class OcorrenciaBean {
    @PersistenceContext
    private EntityManager entityManager;

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
                .createNamedQuery("getAllOcorrenciaByCliente", Ocorrencia.class)
                .setParameter("cliente_id", id)
                .getResultStream()
                .collect(Collectors.toList());
    }

    public Collection<Ocorrencia> findByClienteRecente(Integer id, Integer limite) {
        return entityManager
                .createNamedQuery("getAllOcorrenciaByClienteRecente", Ocorrencia.class)
                .setParameter("cliente_id", id)
                .setMaxResults(limite)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
