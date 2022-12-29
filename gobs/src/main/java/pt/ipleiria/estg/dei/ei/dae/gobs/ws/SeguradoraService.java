package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.SeguradoraBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("seguradoras")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class SeguradoraService {
    @EJB
    private SeguradoraBean seguradoraBean;

    @GET
    @Path("/")
    public Response getAllSeguradoras() {
        return Response.ok(seguradoraBean.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getSeguradora(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Seguradora seguradora = seguradoraBean.find(id);
        if (seguradora == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Seguradora, Seguradora não existe");

        return Response.ok(seguradora).build();
    }
}
