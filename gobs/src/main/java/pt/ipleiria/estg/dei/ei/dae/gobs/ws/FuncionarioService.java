package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.FuncionarioBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Funcionario;
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
import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("funcionarios")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({FUNCIONARIO_ROLE})
public class FuncionarioService {
    @EJB
    private FuncionarioBean funcionarioBean;

    @GET
    @Path("/")
    public Response getAllFuncionario() {
        Collection<Funcionario> funcionarios = funcionarioBean.getFuncionarios();
        return Response.ok(funcionarios.stream().map(Funcionario::toDTO).collect(Collectors.toList())).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({CLIENTE_ROLE, FUNCIONARIO_ROLE})
    public Response getFuncionario(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Funcionario funcionario = funcionarioBean.getFuncionario(id);
        if (funcionario == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Funcionario, Funcionario não existe");

        return Response.ok(funcionario.toDTO()).build();
    }
}