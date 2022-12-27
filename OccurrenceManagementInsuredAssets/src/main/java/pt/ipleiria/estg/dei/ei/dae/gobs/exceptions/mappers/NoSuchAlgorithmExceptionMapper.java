package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Provider
public class NoSuchAlgorithmExceptionMapper implements ExceptionMapper<NoSuchAlgorithmException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(NoSuchAlgorithmException ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
