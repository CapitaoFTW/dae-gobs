package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ClienteBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("apolices")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
public class ApolicesService {
    @EJB
    private ClienteBean clienteBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getAllApolices() {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        return Response.ok(clienteBean.getApolices(id)).build();//todo dto
    }

    @GET
    @Path("/{apoliceId}")
    public Response getApolice(@PathParam("apoliceId") Integer apoliceId) {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        return Response.ok(clienteBean.getApolice(id, apoliceId)).build();//todo dto
    }
}
