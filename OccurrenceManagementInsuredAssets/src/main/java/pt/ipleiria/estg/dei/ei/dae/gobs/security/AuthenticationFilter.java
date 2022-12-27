package pt.ipleiria.estg.dei.ei.dae.gobs.security;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.User;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotAuthorizedException;
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
    private UserBean userBean;

    @Override
    @Transactional
    public void filter(ContainerRequestContext containerRequestContext) {
        String authorization = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer "))
            throw new NotAuthorizedException("Authorization header must be provided");

        String jwToken = authorization.substring("Bearer".length()).trim();
        AuthInfo authInfo = issuer.revertIssue(jwToken);

        User user = userBean.find(authInfo.getUsername());
        if (user == null || !authInfo.getToken().equals(user.getToken()))
            throw new NotAuthorizedException("Authorization received an invalid token");

        containerRequestContext.setSecurityContext(new SecurityContext() {
            //todo maybe adding roles
            //final Collection<Role> roles = new LinkedHashSet<>(user.getRoles());

            @Override
            public Principal getUserPrincipal() {
                return user::getUsername;
            }

            @Override
            public boolean isUserInRole(String s) {
                //todo related to roles
                /*for (Role role : roles) {
                    if (role.getRoleId().equals(s))
                        return true;
                }*/

                return Hibernate.getClass(user).getSimpleName().equals(s);
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