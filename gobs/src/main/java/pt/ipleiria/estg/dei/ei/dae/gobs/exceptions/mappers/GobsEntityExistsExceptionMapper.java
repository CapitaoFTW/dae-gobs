package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityExistsException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class GobsEntityExistsExceptionMapper implements ExceptionMapper<GobsEntityExistsException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(GobsEntityExistsException ex) {
        return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
    }
}