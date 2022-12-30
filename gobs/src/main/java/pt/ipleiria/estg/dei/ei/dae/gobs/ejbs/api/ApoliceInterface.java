package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/apolices")
@Produces({MediaType.APPLICATION_JSON})
public interface ApoliceInterface {
    @GET
    @Path("/")
    Collection<Apolice> getAllApolices();

    @GET
    @Path("/{apoliceId}")
    Apolice getApolice(@PathParam("apoliceId") Integer apoliceId);
}
