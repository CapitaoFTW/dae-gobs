package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ClienteParticularDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.PeritoDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private ClienteParticularBean clienteParticularBean;
    @EJB
    private PeritoBean peritoBean;
    @Inject
    private Logger logger;

    @PostConstruct
    public void postConstruct() {
        try {
            logger.info("Populating DB...");
            populateDB();
            logger.info("Populate Completed!!!");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "!!! Fail to populate DB !!!", ex);
        }
    }

    private void populateDB() throws NoSuchAlgorithmException {
        clienteParticularBean.create(new ClienteParticularDTO(123456789, "test"));
        peritoBean.create(new PeritoDTO("test", "test"));
    }
}
