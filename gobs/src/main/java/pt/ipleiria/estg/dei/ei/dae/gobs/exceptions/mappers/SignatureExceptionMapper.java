package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.mappers;

import io.jsonwebtoken.security.SignatureException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SignatureExceptionMapper implements ExceptionMapper<SignatureException> {
    @Override
    public Response toResponse(SignatureException ex) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
