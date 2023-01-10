package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ApoliceDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ApoliceBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ClienteBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.SeguradoraBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("apolices")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})//todo change when implement funcionario
public class ApolicesService {
    @Inject
    Logger logger;
    @EJB
    private ApoliceBean apoliceBean;
    @EJB
    private ClienteBean clienteBean;
    @EJB
    private SeguradoraBean seguradoraBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getAllApolices() {//todo change when implement funcionario
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Apolice> apolices = apoliceBean.getClienteApolices(id);
        return Response.ok(apolicesToDTOs(apolices, false)).build();
    }

    @GET
    @Path("/recent")
    public Response getAllApolicesRecentes(@DefaultValue("50") @QueryParam("limit") Integer limit) {//todo change when implement funcionario
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Apolice> apolices = apoliceBean.getClienteApolicesRecent(id, limit);
        return Response.ok(apolicesToDTOs(apolices, false)).build();
    }

    private Collection<ApoliceDTO> apolicesToDTOs(Collection<Apolice> apolices, boolean needCliente) {
        Collection<ApoliceDTO> apolicesDTOs = new LinkedList<>();
        Map<Integer, Cliente> clientes = new LinkedHashMap<>();
        Map<Integer, Seguradora> seguradoras = new LinkedHashMap<>();

        for (Apolice apolice : apolices) {
            ApoliceDTO dto = apolice.toDto();
            apolicesDTOs.add(dto);

            Integer seguradoraId = apolice.getSeguradoraId();
            Seguradora seguradora = seguradoras.get(seguradoraId);
            if (seguradora == null) {
                seguradora = seguradoraBean.getSeguradora(seguradoraId);
                seguradoras.put(seguradoraId, seguradora);
            }
            dto.setSeguradora(seguradora);

            //noinspection DuplicatedCode
            if (needCliente) {
                Integer clienteId = apolice.getClienteId();
                Cliente cliente = clientes.get(clienteId);
                if (cliente == null) {
                    cliente = clienteBean.getCliente(clienteId);
                    clientes.put(clienteId, cliente);
                }
                dto.setCliente(cliente.toDto());
            }
        }

        return apolicesDTOs;
    }
}
