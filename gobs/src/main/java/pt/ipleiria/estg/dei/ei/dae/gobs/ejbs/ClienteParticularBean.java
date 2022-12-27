package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ClienteParticularDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.ClienteParticular;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.security.NoSuchAlgorithmException;

@Stateless
public class ClienteParticularBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public ClienteParticular create(ClienteParticularDTO dto) throws NoSuchAlgorithmException {
        Integer nif = dto.getNif();
        if (exists(nif))
            throw new GobsEntityExistsException(nif, "Falha ao criar um novo cliente, o cliente já existe.");

        String password = dto.getPassword();
        password = hasher.hash(password);
        dto.setPassword(password);

        ClienteParticular clienteParticular = dto.toEntity();
        try {
            entityManager.persist(clienteParticular);
        } catch (ConstraintViolationException ex) {
            throw new GobsConstraintViolationException(ex);
        }
        return clienteParticular;
    }

    public boolean exists(Integer nif) {
        return entityManager.createNamedQuery("existsCliente", Long.class).setParameter("nif", nif).getSingleResult() > 0;
    }
}
