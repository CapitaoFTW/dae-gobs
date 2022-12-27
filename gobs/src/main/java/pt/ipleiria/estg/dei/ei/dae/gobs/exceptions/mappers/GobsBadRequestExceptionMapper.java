package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsBadRequestException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class GobsBadRequestExceptionMapper implements ExceptionMapper<GobsBadRequestException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(GobsBadRequestException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }
}
