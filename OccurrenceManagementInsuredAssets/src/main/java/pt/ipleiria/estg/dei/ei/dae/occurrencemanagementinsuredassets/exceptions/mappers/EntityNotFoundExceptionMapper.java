package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.exceptions.EntityNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(EntityNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(ex.getComposedMessage()).build();
    }
}
