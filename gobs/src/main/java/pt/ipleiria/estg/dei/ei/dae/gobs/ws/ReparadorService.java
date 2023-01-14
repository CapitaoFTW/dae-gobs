package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ReparadorDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ReparadorBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Reparador;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.REPARADOR_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({REPARADOR_ROLE})
@Path("reparadores")
public class ReparadorService {
    @EJB
    private ReparadorBean reparadorBean;
    @EJB
    private EmailBean emailBean;

    @GET
    @Path("/")
    public Response getAllReparadores() {
        Collection<ReparadorDTO> reparadorDTOS = reparadorBean.getReparadores().stream().map(Reparador::toDto).collect(Collectors.toList());
        return Response.ok(reparadorDTOS).build();
    }

    /*@POST
    @Path("/{username}/email/send")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email) throws MessagingException {
        Reparador reparador = reparadorBean.findOrFail(username);

        emailBean.send(reparador.getEmail(), reparador.getSubject(), reparador.getMessage()); TODO
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getReparador(@PathParam("id") Integer id) throws GobsEntityNotFoundException {
        Reparador reparador = reparadorBean.getReparador(id);
        if (reparador == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter Reparador, Reparador não existe");

        return Response.ok(reparador.toDto()).build();
    }*/
}