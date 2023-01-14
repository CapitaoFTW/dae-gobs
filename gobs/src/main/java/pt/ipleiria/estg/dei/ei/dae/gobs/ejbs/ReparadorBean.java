package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Reparador;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.stream.Collectors;

@Stateless
public class ReparadorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Collection<Reparador> getReparadores() {
        return entityManager
                .createNamedQuery("getReparadores", Reparador.class)
                .getResultStream()
                .collect(Collectors.toList());
    }

    public Reparador find(String username) {
        return entityManager.find(Reparador.class, username);
    }

    public Reparador findOrFail(String username) {
        var reparador = entityManager.getReference(Reparador.class, username);

        return reparador;
    }
}
