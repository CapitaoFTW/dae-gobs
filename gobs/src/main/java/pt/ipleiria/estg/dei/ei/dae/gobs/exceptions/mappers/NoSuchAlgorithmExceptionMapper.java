package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.security.NoSuchAlgorithmException;

@Provider
public class NoSuchAlgorithmExceptionMapper implements ExceptionMapper<NoSuchAlgorithmException> {
    @Override
    public Response toResponse(NoSuchAlgorithmException ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
