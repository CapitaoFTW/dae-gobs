package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.SeguradoraInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.SeguradoraProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.function.Function;

@Stateless
public class SeguradoraBean {
    @EJB
    private SeguradoraProxy seguradoraProxy;
    private SeguradoraInterface seguradoraBridge;

    public Seguradora find(Integer seguradoraId) {
        return wrapRequest(b -> b.getSeguradora(seguradoraId));
    }

    public Collection<Seguradora> getAll() {
        return wrapRequest(SeguradoraInterface::getSeguradoras);
    }

    public Collection<Apolice> getApolices(Integer seguradoraId) {
        return wrapRequest(b -> b.getApolices(seguradoraId));
    }

    public Apolice getApolice(Integer seguradoraId, Integer apoliceId) {
        return wrapRequest(b -> b.getApolice(seguradoraId, apoliceId));
    }

    private SeguradoraInterface getBridge() {
        if (seguradoraBridge == null)
            seguradoraBridge = seguradoraProxy.getProxy();

        return seguradoraBridge;
    }

    private <R> R wrapRequest(Function<SeguradoraInterface, R> function) {
        try {
            return function.apply(getBridge());
        } catch (WebApplicationException ex) {
            if (ex.getResponse().getStatusInfo() == Response.Status.INTERNAL_SERVER_ERROR)//MockAPi.io returns error 500 instead of 404
                return null;

            throw ex;
        }
    }
}

