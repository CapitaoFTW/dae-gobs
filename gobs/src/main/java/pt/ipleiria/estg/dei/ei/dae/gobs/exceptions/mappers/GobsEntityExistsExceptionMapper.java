package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GobsEntityExistsExceptionMapper implements ExceptionMapper<GobsEntityExistsException> {
    @Override
    public Response toResponse(GobsEntityExistsException ex) {
        return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
    }
}