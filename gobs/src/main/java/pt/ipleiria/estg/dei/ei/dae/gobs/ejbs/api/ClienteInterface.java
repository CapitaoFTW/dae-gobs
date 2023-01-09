package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Consumes({MediaType.APPLICATION_JSON})
@Path("/clientes")
@Produces({MediaType.APPLICATION_JSON})
public interface ClienteInterface {
    @GET
    @Path("/")
    Collection<Cliente> getClientes();

    @GET
    @Path("/{id}")
    Cliente getCliente(@PathParam("id") Integer id);

    @GET
    @Path("/")
    Collection<Cliente> getClienteByNif(@QueryParam("nif") Integer nif);

    @Path("/{id}")
    @PUT
    Cliente updateCliente(@PathParam("id") Integer id, Cliente cliente);
}

