package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import io.jsonwebtoken.security.SignatureException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static pt.ipleiria.estg.dei.ei.dae.gobs.security.AuthorizationFilter.ACCESS_DENIED;

@Provider
public class SignatureExceptionMapper implements ExceptionMapper<SignatureException> {
    @Override
    public Response toResponse(SignatureException ex) {
        return ACCESS_DENIED;
    }
}
