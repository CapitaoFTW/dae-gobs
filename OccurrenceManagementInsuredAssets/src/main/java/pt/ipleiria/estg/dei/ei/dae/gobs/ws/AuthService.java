package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ChangePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.AuthInfo;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.TokenIssuer;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@Consumes({MediaType.APPLICATION_JSON})
@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
public class AuthService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private UserBean userBean;
    @Inject
    private TokenIssuer issuer;

    @Path("/login")
    @POST
    public Response authenticate(@Valid AuthDTO auth) throws NoSuchAlgorithmException {
        try {
            AuthInfo authInfo = userBean.canLogin(auth);
            if (authInfo == null)
                throw new NotAuthorizedException("Invalid login credentials");

            return issueAuthToken(authInfo);
        } catch (EntityNotFoundException ex) {
            throw new NotAuthorizedException("Invalid login credentials");
        }
    }

    @Authenticated
    @PATCH
    @Path("/change-password")
    public Response changePassword(@Valid ChangePasswordDTO dto) throws NoSuchAlgorithmException {
        Principal principal = securityContext.getUserPrincipal();
        AuthInfo authInfo = userBean.changePassword(principal.getName(), dto);
        if (authInfo == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("OldPassword is wrong or newPassword and confirmPassword do not match").build();

        return issueAuthToken(authInfo);
    }

    protected Response issueAuthToken(AuthInfo authInfo) {
        String jwToken = issuer.issue(authInfo);
        return Response.ok(jwToken).build();
    }
}
