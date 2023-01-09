package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.OcorrenciaBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("ocorrencias")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
public class OcorrenciaService {
    @EJB
    private OcorrenciaBean ocorrenciaBean;

    @GET
    @Path("/cliente/{id}")
    public Response getOcorrenciasByCliente(@PathParam("id") Integer id) {
        Collection<Ocorrencia> ocorrencias = ocorrenciaBean.findByCliente(id);
        if (ocorrencias == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Ocorrencias, Cliente não existe");

        return Response.ok(ocorrencias).build();//todo dto
    }

    @GET
    @Path("/cliente/{id}/recent")
    public Response getOcorrenciaByClienteRecente(@PathParam("id") Integer id, @QueryParam("limit") Integer limit) {
        Collection<Ocorrencia> ocorrencias = ocorrenciaBean.findByClienteRecente(id, limit);
        if (ocorrencias == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Ocorrencias, Cliente não existe");

        return Response.ok(ocorrencias).build();//todo dto
    }
}
