package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Funcionario;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Consumes({MediaType.APPLICATION_JSON})
@Path("/funcionarios")
@Produces({MediaType.APPLICATION_JSON})
public interface FuncionarioInterface {
    @GET
    @Path("/")
    Collection<Funcionario> getFuncionarios();

    @GET
    @Path("/{id}")
    Funcionario getFuncionario(@PathParam("id") Integer id);

    @GET
    @Path("/")
    Collection<Funcionario> getFuncionarioByUsername(@QueryParam("username") String username);

    @Path("/{id}")
    @PUT
    Funcionario updateFuncionario(@PathParam("id") Integer id, Funcionario funcionario);
}
