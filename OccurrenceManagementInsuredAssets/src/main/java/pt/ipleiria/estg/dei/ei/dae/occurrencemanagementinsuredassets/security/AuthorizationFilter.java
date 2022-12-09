package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.security;

import org.jboss.resteasy.core.ResourceMethodInvoker;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.Arrays;

@Authenticated
@Priority(Priorities.AUTHORIZATION)
@Provider
public class AuthorizationFilter implements ContainerRequestFilter {
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).entity("Access denied").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("Access forbidden").build();

    @Context
    private SecurityContext securityContext;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) containerRequestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        if (method.isAnnotationPresent(PermitAll.class))//If method Permit All then OK
            return;

        if (method.isAnnotationPresent(DenyAll.class)) {//If method Deny All then Deny
            containerRequestContext.abortWith(ACCESS_DENIED);
            return;
        }

        RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
        if (rolesAnnotation != null) {//If method have Roles check for them
            if (Arrays.stream(rolesAnnotation.value()).anyMatch(securityContext::isUserInRole)) {
                return;
            }

            containerRequestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }

        //Since all method checks are done
        //Lets check for class perms then

        Class<?> resource = method.getDeclaringClass();
        if (resource.isAnnotationPresent(PermitAll.class))//If class Permit All then OK
            return;

        if (resource.isAnnotationPresent(DenyAll.class)) {//If class Deny All then Deny
            containerRequestContext.abortWith(ACCESS_DENIED);
            return;
        }

        //Getting roles from class
        rolesAnnotation = resource.getAnnotation(RolesAllowed.class);
        if (rolesAnnotation != null) {//If class have Roles check for them
            if (Arrays.stream(rolesAnnotation.value()).anyMatch(securityContext::isUserInRole)) {
                return;
            }

            containerRequestContext.abortWith(ACCESS_FORBIDDEN);
            //noinspection UnnecessaryReturnStatement
            return;
        }
    }
}
