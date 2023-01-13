package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Consumes({MediaType.APPLICATION_JSON})
@Path("/apolices")
@Produces({MediaType.APPLICATION_JSON})
public interface ApoliceInterface {
    @GET
    @Path("/")
    Collection<Apolice> getApolices();

    @GET
    @Path("/")
    Collection<Apolice> getApolices(
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") OrderEnum order
    );

    @GET
    @Path("/")
    Collection<Apolice> getApolices(
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") OrderEnum order,
            @QueryParam("limit") Integer limit,
            @QueryParam("page") Integer page
    );

    @GET
    @Path("/")
    Collection<Apolice> getApolices(
            @QueryParam("clienteId") Integer clienteId
    );

    @GET
    @Path("/")
    Collection<Apolice> getApolices(
            @QueryParam("clienteId") Integer clienteId,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") OrderEnum order
    );

    @GET
    @Path("/")
    Collection<Apolice> getApolices(
            @QueryParam("clienteId") Integer clienteId,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") OrderEnum order,
            @QueryParam("limit") Integer limit,
            @QueryParam("page") Integer page
    );

    @GET
    @Path("/{id}")
    Apolice getApolice(@PathParam("id") Integer id);

    @Path("/{id}")
    @PUT
    Apolice updateApolice(@PathParam("id") Integer id, Apolice apolice);
}
