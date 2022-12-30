package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/seguradoras")
@Produces({MediaType.APPLICATION_JSON})
public interface SeguradoraInterface {
    @GET
    @Path("/")
    Collection<Seguradora> getSeguradoras();

    @GET
    @Path("/{seguradoraId}")
    Seguradora getSeguradora(@PathParam("seguradoraId") Integer seguradoraId);

    @GET
    @Path("/{seguradoraId}/apolices")
    Collection<Apolice> getApolices(@PathParam("seguradoraId") Integer seguradoraId);

    @GET
    @Path("/{seguradoraId}/apolices/{apoliceId}")
    Apolice getApolice(@PathParam("seguradoraId") Integer seguradoraId, @PathParam("apoliceId") Integer apoliceId);
}
