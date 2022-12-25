package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.exceptions.mappers;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class CatchUnhandledExceptionMapper implements ExceptionMapper<Exception> {
    @Inject
    private Logger logger;

    @Context
    private Providers providers;

    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof EJBException) { //Unwrap exception and try
            logger.log(Level.INFO, "Wrapped exception detected! Unwrapping...");
            ex = ((EJBException) ex).getCausedByException();

            //Get mapper for wrapped exception and propagate it
            Response response = getMapperAndPropagateException(ex.getClass(), ex);
            if (response != null)
                return response;
        }

        logger.log(Level.WARNING, "UnhandledException: %s", ex);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
    }

    private <T extends Throwable> Response getMapperAndPropagateException(Class<T> exClass, Exception ex) {
        ExceptionMapper<T> mapper = providers.getExceptionMapper(exClass);
        if (mapper == null)
            return null;

        return mapper.toResponse(exClass.cast(ex));
    }
}
