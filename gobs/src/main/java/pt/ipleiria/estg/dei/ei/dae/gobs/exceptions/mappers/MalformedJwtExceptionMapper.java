package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import io.jsonwebtoken.MalformedJwtException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static pt.ipleiria.estg.dei.ei.dae.gobs.security.AuthorizationFilter.ACCESS_DENIED;

@Provider
public class MalformedJwtExceptionMapper implements ExceptionMapper<MalformedJwtException> {
    @Override
    public Response toResponse(MalformedJwtException ex) {
        return ACCESS_DENIED;
    }
}
