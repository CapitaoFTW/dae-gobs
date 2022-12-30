package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.OcorrenciaBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Consumes({MediaType.APPLICATION_JSON})
@Path("ocorrencias")
@Produces({MediaType.APPLICATION_JSON})
public class OcorrenciaService {
    @EJB
    private OcorrenciaBean ocorrenciaBean;

    @GET
    @Path("/cliente/{nif}")
    public Response getAllOcorrencias(@PathParam("nif") Integer nif) {
        Collection<Ocorrencia> ocorrencias = ocorrenciaBean.findByCliente(nif);
        if (ocorrencias == null)
            throw new GobsEntityNotFoundException(nif, "Falha ao obter Ocorrencias, Cliente não existe");

        return Response.ok(ocorrencias).build();
    }
}
