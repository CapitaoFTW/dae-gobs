package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class GobsEntityNotFoundExceptionMapper implements ExceptionMapper<GobsEntityNotFoundException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(GobsEntityNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(ex.getComposedMessage()).build();
    }
}
