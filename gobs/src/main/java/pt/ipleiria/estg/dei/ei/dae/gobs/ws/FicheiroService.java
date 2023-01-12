package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.FicheiroBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ficheiro;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;
import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("ficheiros")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE, FUNCIONARIO_ROLE})
public class FicheiroService {
    @EJB
    private FicheiroBean ficheiroBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFicheiro(@PathParam("id") Long id) {
        Ficheiro ficheiro = ficheiroBean.find(id);
        if (ficheiro == null)
            throw new GobsEntityNotFoundException(id, "Falha ao obter ficheiro, ficheiro não encontrado.");

        if (!securityContext.isUserInRole(FUNCIONARIO_ROLE)) {
            Integer clientId = Integer.valueOf(securityContext.getUserPrincipal().getName());
            if (!ficheiro.getOcorrencia().getId().equals(clientId))
                return Response.status(Response.Status.FORBIDDEN).build();
        }

        File file = new File(ficheiro.getFilepath());
        return Response.ok(file)
                .header("Content-Disposition", "attachment;filename" + ficheiro.getFilename())
                .build();//todo add info
    }
}
