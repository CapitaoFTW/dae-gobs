package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import org.jboss.resteasy.client.exception.ResteasyClientErrorException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class ResteasyClientErrorExceptionMapper implements ExceptionMapper<ResteasyClientErrorException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(ResteasyClientErrorException ex) {
        Response response = ex.getResponse();
        logger.warning(String.format("Falha em pedido para MockApi: %s | Entity: %s", response.getStatusInfo(), response.getEntity()));
        return Response.status(response.getStatus()).entity("Falha ao fazer o pedido para MockApi").build();
    }
}
