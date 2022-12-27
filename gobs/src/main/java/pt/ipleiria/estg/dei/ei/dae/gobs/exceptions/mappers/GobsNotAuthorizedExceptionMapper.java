package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsNotAuthorizedException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class GobsNotAuthorizedExceptionMapper implements ExceptionMapper<GobsNotAuthorizedException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(GobsNotAuthorizedException ex) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
    }
}
