package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.exceptions.mappers;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(NotAuthorizedException ex) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
