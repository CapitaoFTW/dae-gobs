package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ws;

import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ejbs.SeguradoraBean;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities.Seguradora;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes({MediaType.APPLICATION_JSON})
@Path("seguradoras")
@Produces({MediaType.APPLICATION_JSON})
public class SeguradoraService {
    @EJB
    private SeguradoraBean seguradoraBean;

    @GET
    @Path("/")
    public Response getAll() {
        return Response.ok(seguradoraBean.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getSeguradora(@PathParam("id") int id) {
        Seguradora seguradora = seguradoraBean.find(id);
        if (seguradora == null)
            throw new NotFoundException("Falha ao obter Seguradora, Seguradora não existe.");

        return Response.ok(seguradora).build();
    }
}
