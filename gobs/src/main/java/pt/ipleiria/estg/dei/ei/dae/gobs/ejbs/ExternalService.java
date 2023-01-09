package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.ResteasyProxy;

import javax.persistence.MappedSuperclass;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.function.Function;

@MappedSuperclass
public abstract class ExternalService<Bridge, Proxy extends ResteasyProxy<Bridge>> {
    protected Bridge bridge;

    protected abstract Proxy getProxy();

    protected Bridge getBridge() {
        if (bridge == null)
            bridge = getProxy().getBridge();

        return bridge;
    }

    protected <R> R wrapRequest(Function<Bridge, R> function) {
        try {
            return function.apply(getBridge());
        } catch (WebApplicationException ex) {
            if (ex.getResponse().getStatusInfo() == Response.Status.INTERNAL_SERVER_ERROR)//MockAPi.io returns error 500 instead of 404
                return null;

            throw ex;
        }
    }
}
