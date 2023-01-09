package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private OcorrenciaBean ocorrenciaBean;
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

    private void populateDB() {
        final Random random = new Random();
        final EstadoOcorrencia[] estados = EstadoOcorrencia.values();
        for (int i = 0; i < 30; i++) {
            EstadoOcorrencia estadoOcorrencia = estados[random.nextInt(estados.length)];
            ocorrenciaBean.create(new Ocorrencia(i % 10 + 1, estadoOcorrencia));
        }
    }
}