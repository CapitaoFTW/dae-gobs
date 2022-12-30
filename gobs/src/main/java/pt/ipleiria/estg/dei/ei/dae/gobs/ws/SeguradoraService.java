package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.SeguradoraBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("seguradoras")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SeguradoraService {//todo DTOs
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

    @GET
    @Path("/{id}/apolices")
    public Response getApolices(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Collection<Apolice> apolices = seguradoraBean.getApolices(id);
        if (apolices == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Apolices, Seguradora não existe");

        return Response.ok(apolices).build();
    }

    @GET
    @Path("/{seguradoraId}/apolices/{apoliceId}")
    public Response getApolice(@PathParam("seguradoraId") Integer seguradoraId, @PathParam("apoliceId") Integer apoliceId) throws GobsEntityNotFoundException {
        Apolice apolice = seguradoraBean.getApolice(seguradoraId, apoliceId);
        if (apolice == null)
            throw new GobsEntityNotFoundException(String.format("%d-%d", seguradoraId, apoliceId), "Falha ao obter apolice");

        return Response.ok(apolice).build();
    }
}