package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ApoliceInterface;

import javax.ejb.Singleton;

@Singleton
public class ApoliceProxy extends ResteasyProxy<ApoliceInterface> {
    public ApoliceProxy() {
        super(ApoliceInterface.class);
    }
}
