package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ClienteInterface;

import javax.ejb.Singleton;

@Singleton
public class ClienteProxy extends ResteasyProxy<ClienteInterface> {
    public ClienteProxy() {
        super(ClienteInterface.class);
    }
}
