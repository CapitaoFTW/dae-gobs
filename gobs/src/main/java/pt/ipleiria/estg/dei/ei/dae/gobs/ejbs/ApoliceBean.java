package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ApoliceInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.ApoliceProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
public class ApoliceBean {
    @EJB
    private ApoliceProxy apoliceProxy;
    private ApoliceInterface apoliceBridge;

    public Apolice find(Integer id) {
        try {
            return getBridge().getApolice(id);
        } catch (WebApplicationException ex) {
            if (ex.getResponse().getStatusInfo() == Response.Status.INTERNAL_SERVER_ERROR)//MockAPi.io returns error 500 instead of 404
                return null;

            throw ex;
        }
    }

    public Collection<Apolice> getAllApolices() {
        return getBridge().getAllApolices();
    }

    private ApoliceInterface getBridge() {
        if (apoliceBridge == null)
            apoliceBridge = apoliceProxy.getProxy();

        return apoliceBridge;
    }
}

