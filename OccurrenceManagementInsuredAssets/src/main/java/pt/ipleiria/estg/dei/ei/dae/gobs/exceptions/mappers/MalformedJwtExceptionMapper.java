package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import io.jsonwebtoken.MalformedJwtException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MalformedJwtExceptionMapper implements ExceptionMapper<MalformedJwtException> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(MalformedJwtException ex) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
