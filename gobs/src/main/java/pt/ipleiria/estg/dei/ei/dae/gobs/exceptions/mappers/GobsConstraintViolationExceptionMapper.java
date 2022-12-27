package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsConstraintViolationException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class GobsConstraintViolationExceptionMapper implements ExceptionMapper<GobsConstraintViolationException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(GobsConstraintViolationException ex) {
        logger.warning(ex.getComposedMessage());
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}