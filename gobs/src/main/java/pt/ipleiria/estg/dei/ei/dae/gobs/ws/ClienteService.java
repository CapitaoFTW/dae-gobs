package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ClienteBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("clientes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClienteService {//todo DTOs add Authentication
    @EJB
    private ClienteBean clienteBean;

    @GET
    @Path("/")
    public Response getAllClientes() {
        return Response.ok(clienteBean.getClientes()).build();//todo dto
    }

    @GET
    @Path("/{id}")
    public Response getCliente(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Cliente cliente = clienteBean.getCliente(id);
        if (cliente == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Cliente, Cliente não existe");

        return Response.ok(cliente).build();//todo dto
    }
}