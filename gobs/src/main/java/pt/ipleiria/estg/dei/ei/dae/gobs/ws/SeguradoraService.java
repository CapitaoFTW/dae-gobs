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
public class SeguradoraService {//todo DTOs add Authentication
    @EJB
    private SeguradoraBean seguradoraBean;

    @GET
    @Path("/")
    public Response getAllSeguradoras() {
        return Response.ok(seguradoraBean.getSeguradoras()).build();//todo dto
    }

    @GET
    @Path("/{id}")
    public Response getSeguradora(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Seguradora seguradora = seguradoraBean.getSeguradora(id);
        if (seguradora == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Seguradora, Seguradora não existe");

        return Response.ok(seguradora).build();//todo dto
    }
}