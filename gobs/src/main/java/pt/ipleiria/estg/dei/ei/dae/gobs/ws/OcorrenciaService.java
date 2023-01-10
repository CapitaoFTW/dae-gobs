package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.OcorrenciaBean;
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
@Path("ocorrencias")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
public class OcorrenciaService {
    @EJB
    private OcorrenciaBean ocorrenciaBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getOcorrenciasByCliente() {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        return Response.ok(ocorrenciaBean.findByCliente(id)).build();//todo dto
    }

    @GET
    @Path("/recent")
    public Response getOcorrenciaByClienteRecente(@DefaultValue("50") @QueryParam("limit") Integer limit) {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        return Response.ok(ocorrenciaBean.findByClienteRecente(id, limit)).build();//todo dto
    }
}
