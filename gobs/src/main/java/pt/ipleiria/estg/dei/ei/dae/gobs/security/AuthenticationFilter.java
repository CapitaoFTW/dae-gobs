package pt.ipleiria.estg.dei.ei.dae.gobs.security;

import org.jboss.resteasy.plugins.server.embedded.SimplePrincipal;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsNotAuthorizedException;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.security.Principal;

@Authenticated
@Priority(Priorities.AUTHENTICATION)
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    @Inject
    private TokenIssuer issuer;
    @Context
    private UriInfo uriInfo;
    @EJB
    private AuthBean authBean;

    @Override
    @Transactional
    public void filter(ContainerRequestContext containerRequestContext) {
        String authorization = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer "))
            throw new GobsNotAuthorizedException("O cabeçalho de autenticação está em falta.");

        String jwToken = authorization.substring("Bearer".length()).trim();
        AuthInfo authInfo = issuer.revertIssue(jwToken);

        String mainRole = authInfo.getMainRole();
        String token = authBean.getToken(Integer.valueOf(authInfo.getEntityId()), mainRole);

        if (!authInfo.getToken().equals(token))
            throw new GobsNotAuthorizedException("O autenticador recebeu um token inválido.");

        containerRequestContext.setSecurityContext(new SecurityContext() {
            //todo maybe adding roles
            //final Collection<Role> roles = new LinkedHashSet<>(user.getRoles());

            @Override
            public Principal getUserPrincipal() {
                return new SimplePrincipal(authInfo.getEntityId());
            }

            @Override
            public boolean isUserInRole(String s) {
                //todo related to roles
                /*for (Role role : roles) {
                    if (role.getRoleId().equals(s))
                        return true;
                }*/

                return mainRole.equals(s);
            }

            @Override
            public boolean isSecure() {
                return uriInfo.getAbsolutePath().toString().startsWith("https");
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }
}
