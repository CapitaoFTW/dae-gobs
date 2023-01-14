package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ApoliceBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ClienteBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;
import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;
import static pt.ipleiria.estg.dei.ei.dae.gobs.security.AuthorizationFilter.ACCESS_FORBIDDEN;

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
    @EJB
    private ClienteBean clienteBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getAllApolices() {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Apolice> apolices = apoliceBean.getClienteApolices(id);
        return Response.ok(apolices.stream().map(Apolice::toDTO).collect(Collectors.toList())).build();
    }

    @GET
    @Path("/recent")
    public Response getAllApolicesRecentes(@DefaultValue("50") @QueryParam("limit") Integer limit) {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Apolice> apolices = apoliceBean.getClienteApolicesRecent(id, limit);
        return Response.ok(apolices.stream().map(Apolice::toDTO).collect(Collectors.toList())).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({CLIENTE_ROLE, FUNCIONARIO_ROLE})
    public Response getApolice(@PathParam("id") Integer apoliceId) {
        Apolice apolice = apoliceBean.getApolice(apoliceId);
        if (apolice == null)
            throw new GobsEntityNotFoundException(apoliceId, "Falha ao obter Apólice, Apólice não existe");

        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
            if (!apolice.getClienteId().equals(id))
                return ACCESS_FORBIDDEN;
        }

        return Response.ok(apolice.toDTO()).build();
    }
}
