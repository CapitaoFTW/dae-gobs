package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.SeguradoraInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.SeguradoraProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
public class SeguradoraBean {
    @EJB
    private SeguradoraProxy seguradoraProxy;
    private SeguradoraInterface seguradoraBridge;

    public Seguradora find(Integer id) {
        try {
            return getBridge().getSeguradora(id);
        } catch (WebApplicationException ex) {
            if (ex.getResponse().getStatusInfo() == Response.Status.INTERNAL_SERVER_ERROR)//MockAPi.io returns error 500 instead of 404
                return null;

            throw ex;
        }
    }

    public Collection<Seguradora> getAll() {
        return getBridge().getAll();
    }

    private SeguradoraInterface getBridge() {
        if (seguradoraBridge == null)
            seguradoraBridge = seguradoraProxy.getProxy();

        return seguradoraBridge;
    }
}

