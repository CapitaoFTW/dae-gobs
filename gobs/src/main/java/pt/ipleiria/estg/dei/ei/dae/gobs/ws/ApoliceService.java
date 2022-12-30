package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ApoliceBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("apolices")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class ApoliceService {
    @EJB
    private ApoliceBean apoliceBean;

    @GET
    @Path("/")
    public Response getAllApolices() {
        return Response.ok(apoliceBean.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getSeguradora(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Apolice apolice = apoliceBean.find(id);
        if (apolice == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter apólice, apólice não existe");

        return Response.ok(apolice).build();
    }
}
