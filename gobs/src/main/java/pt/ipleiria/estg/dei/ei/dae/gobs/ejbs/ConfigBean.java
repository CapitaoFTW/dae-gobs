package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
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

    }
}