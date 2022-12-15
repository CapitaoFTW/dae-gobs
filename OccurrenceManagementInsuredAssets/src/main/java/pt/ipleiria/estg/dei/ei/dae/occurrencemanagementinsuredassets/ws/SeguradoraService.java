package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ws;

import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ejbs.SeguradoraBean;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes({MediaType.APPLICATION_JSON})
@Path("seguradoras")
@Produces({MediaType.APPLICATION_JSON})
public class SeguradoraService {
    @EJB
    private SeguradoraBean seguradoraBean;

    @GET
    @Path("/")
    public Response getAll() {
        return Response.ok(seguradoraBean.getAll()).build();
    }
}
