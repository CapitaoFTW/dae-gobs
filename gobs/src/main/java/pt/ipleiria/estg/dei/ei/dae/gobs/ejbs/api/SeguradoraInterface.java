package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Consumes({MediaType.APPLICATION_JSON})
@Path("/seguradoras")
@Produces({MediaType.APPLICATION_JSON})
public interface SeguradoraInterface {
    @GET
    @Path("/")
    Collection<Seguradora> getSeguradoras();

    @GET
    @Path("/{id}")
    Seguradora getSeguradora(@PathParam("id") Integer id);
}