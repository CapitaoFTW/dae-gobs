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

    public boolean exists(Integer nif) {
        return entityManager.createNamedQuery("existsOcorrencia", Long.class).setParameter("nif", nif).getSingleResult() > 0;
    }

    public Ocorrencia find(Integer nif) {
        return find(nif, LockModeType.OPTIMISTIC);
    }

    public Ocorrencia find(Integer nif, LockModeType lockModeType) {
        return entityManager.find(Ocorrencia.class, nif, lockModeType);
    }

    public Collection<Ocorrencia> findByCliente(Integer nif) {
        return entityManager
                .createNamedQuery("getAllOcorrenciaByCliente", Ocorrencia.class)
                .setParameter("nif", nif)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
