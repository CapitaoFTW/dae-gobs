package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ficheiro;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsConstraintViolationException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class FicheiroBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public Ficheiro create(Ocorrencia ocorrencia, String filename, String filepath) {
        Ficheiro ficheiro = new Ficheiro(filepath, filename, ocorrencia);
        try {
            entityManager.persist(ficheiro);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }

        ocorrencia.adicionarFicheiro(ficheiro);
        return ficheiro;
    }

    public Ficheiro find(Long id) {
        return find(id, LockModeType.OPTIMISTIC);
    }

    public Ficheiro find(Long id, LockModeType lockModeType) {
        return entityManager.find(Ficheiro.class, id, lockModeType);
    }
}
