package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ChangePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ClienteAuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.UsuarioAuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Funcionario;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsBadRequestException;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsNotAuthorizedException;
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
import java.security.Principal;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;
import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private AuthBean authBean;
    @Inject
    private TokenIssuer issuer;

    @Path("/login-client")
    @POST
    public Response authenticateCliente(@Valid ClienteAuthDTO auth) {
        try {
            AuthInfo authInfo = authBean.canLogin(auth);
            if (authInfo == null)
                throw new GobsNotAuthorizedException("Credenciais de login inválidas.");

            return issueAuthToken(authInfo);
        } catch (EntityNotFoundException ex) {
            throw new GobsNotAuthorizedException("Credenciais de login inválidas.");
        }
    }

    @Path("/login-employee")
    @POST
    public Response authenticateEmployee(@Valid UsuarioAuthDTO auth) {
        try {
            AuthInfo authInfo = authBean.canLogin(auth);
            if (authInfo == null)
                throw new GobsNotAuthorizedException("Credenciais de login inválidas.");

            return issueAuthToken(authInfo);
        } catch (EntityNotFoundException ex) {
            throw new GobsNotAuthorizedException("Credenciais de login inválidas.");
        }
    }

    @Authenticated
    @PATCH
    @Path("/change-password")
    public Response changePassword(@Valid ChangePasswordDTO dto) {
        AuthInfo authInfo;
        Principal principal = securityContext.getUserPrincipal();
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            authInfo = authBean.changeClientePassword(Integer.valueOf(principal.getName()), dto);
        } else if (securityContext.isUserInRole(FUNCIONARIO_ROLE)) {
            authInfo = authBean.changeFuncionarioPassword(Integer.valueOf(principal.getName()), dto);
        } else {
            throw new GobsNotAuthorizedException("Não tem permissão para alterar a sua senha.");
        }

        if (authInfo == null)
            throw new GobsBadRequestException("A senha antiga está errada ou a nova senha e a confirmação da senha não correspondem.");

        return issueAuthToken(authInfo);
    }

    protected Response issueAuthToken(AuthInfo authInfo) {
        String jwToken = issuer.issue(authInfo);
        return Response.ok(jwToken).build();
    }

    @Authenticated
    @GET
    @Path("/self")
    public Response getSelf() {
        Principal principal = securityContext.getUserPrincipal();
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Cliente cliente = authBean.findClienteById(Integer.valueOf(principal.getName()));
            if (cliente == null)
                throw new GobsNotAuthorizedException("Falha ao obter o próprio cliente.");

            return Response.ok(cliente.toDto()).build();
        } else if (securityContext.isUserInRole(FUNCIONARIO_ROLE)) {
            Funcionario funcionario = authBean.findFuncionarioById(Integer.valueOf(principal.getName()));
            if (funcionario == null)
                throw new GobsNotAuthorizedException("Falha ao obter o próprio funcionário.");

            return Response.ok(funcionario.toDto()).build();
        } else {
            throw new GobsNotAuthorizedException("Não tem permissão.");
        }
    }
}
