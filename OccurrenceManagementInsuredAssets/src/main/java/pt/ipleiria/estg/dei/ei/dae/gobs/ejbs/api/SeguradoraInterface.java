package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/Seguradoras")
@Produces({MediaType.APPLICATION_JSON})
public interface SeguradoraInterface {
    @GET
    @Path("/")
    Collection<Seguradora> getAll();

    @GET
    @Path("/{id}")
    Seguradora getSeguradora(@PathParam("id") int id);
}
