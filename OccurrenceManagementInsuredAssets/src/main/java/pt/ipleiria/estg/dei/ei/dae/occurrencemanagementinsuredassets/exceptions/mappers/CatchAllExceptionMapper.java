package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.exceptions.mappers;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof EJBException)//Unwrap exception
            ex = ((EJBException) ex).getCausedByException();

        logger.log(Level.INFO, "Exception: " + ex.getClass().getSimpleName());

        Response.Status status;
        /*if (ex instanceof MyEntityExistsException)
            status = Response.Status.CONFLICT;
        else if (ex instanceof MyEntityNotFoundException)
            status = Response.Status.NOT_FOUND;
        else if (ex instanceof MyConstraintViolationException || ex instanceof MyIllegalArgumentException)
            status = Response.Status.BAD_REQUEST;
        else */if (ex instanceof NotAuthorizedException || ex instanceof MalformedJwtException || ex instanceof SignatureException)
            status = Response.Status.UNAUTHORIZED;
        else if (ex instanceof NoSuchAlgorithmException) {
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        else {
            logger.log(Level.WARNING, "Fail processing app request!", ex);
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)/*.entity(ex.getMessage())*/.build();//todo maybe pass a msg
    }
}
