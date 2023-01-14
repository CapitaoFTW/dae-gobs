package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ficheiro;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.OcorrenciaMensagem;
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

    @SuppressWarnings("UnusedReturnValue")
    public Ficheiro create(OcorrenciaMensagem mensagem, String filename, String mimeType, String filepath) {
        Ficheiro ficheiro = new Ficheiro(filename, mimeType, filepath, mensagem);
        try {
            entityManager.persist(ficheiro);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }

        return ficheiro;
    }

    public Ficheiro find(Integer id) {
        return find(id, LockModeType.OPTIMISTIC);
    }

    public Ficheiro find(Integer id, LockModeType lockModeType) {
        return entityManager.find(Ficheiro.class, id, lockModeType);
    }
}
