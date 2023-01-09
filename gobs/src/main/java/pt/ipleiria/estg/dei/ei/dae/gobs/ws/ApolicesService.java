package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ApoliceBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.logging.Logger;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("apolices")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
public class ApolicesService {
    @Inject
    Logger logger;
    @EJB
    private ApoliceBean apoliceBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getAllApolices() {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        return Response.ok(apoliceBean.getClienteApolices(id)).build();//todo dto
    }

    @GET
    @Path("/recent")
    public Response getAllApolicesRecentes(@QueryParam("limit") Integer limit) {
        logger.finest("limit " + limit);
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        return Response.ok(apoliceBean.getClienteApolicesRecent(id, limit)).build();//todo dto
    }
}
