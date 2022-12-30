package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GobsEntityNotFoundExceptionMapper implements ExceptionMapper<GobsEntityNotFoundException> {
    @Override
    public Response toResponse(GobsEntityNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(ex.getComposedMessage()).build();
    }
}
