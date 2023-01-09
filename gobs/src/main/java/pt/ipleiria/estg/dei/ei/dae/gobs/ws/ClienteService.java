package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ClienteDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ClienteBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("clientes")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
public class ClienteService {
    @EJB
    private ClienteBean clienteBean;

    @GET
    @Path("/")
    public Response getAllClientes() {
        Collection<ClienteDTO> clienteDTOs = clienteBean.getClientes().stream().map(Cliente::toDto).collect(Collectors.toList());
        return Response.ok(clienteDTOs).build();
    }

    @GET
    @Path("/{id}")
    public Response getCliente(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Cliente cliente = clienteBean.getCliente(id);
        if (cliente == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Cliente, Cliente não existe");

        return Response.ok(cliente.toDto()).build();
    }
}