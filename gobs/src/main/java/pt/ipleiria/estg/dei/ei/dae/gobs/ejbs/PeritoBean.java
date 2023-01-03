/*package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.PeritoDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Perito;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.security.NoSuchAlgorithmException;

@Stateless
public class PeritoBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Perito create(PeritoDTO dto) throws NoSuchAlgorithmException {
        String username = dto.getUsername();
        if (exists(username))
            throw new GobsEntityExistsException(username, "Falha ao criar um novo perito, o perito já existe.");

        String password = dto.getPassword();
        password = hasher.hash(password);
        dto.setPassword(password);

        Perito perito = dto.toEntity();
        try {
            entityManager.persist(perito);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }
        return perito;
    }

    public boolean exists(String username) {
        return entityManager.createNamedQuery("existsPerito", Long.class).setParameter("username", username).getSingleResult() > 0;
    }
}*/
