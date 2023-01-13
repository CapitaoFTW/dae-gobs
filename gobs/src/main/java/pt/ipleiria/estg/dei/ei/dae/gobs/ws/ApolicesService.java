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
import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("apolices")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
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
    public Response getAllApolices() {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Apolice> apolices = apoliceBean.getClienteApolices(id);
        return Response.ok(apolicesToDTOs(apolices, false)).build();
    }

    @GET
    @Path("/recent")
    public Response getAllApolicesRecentes(@DefaultValue("50") @QueryParam("limit") Integer limit) {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Apolice> apolices = apoliceBean.getClienteApolicesRecent(id, limit);
        return Response.ok(apolicesToDTOs(apolices, false)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({CLIENTE_ROLE, FUNCIONARIO_ROLE})
    public Response getApolice(@PathParam("id") Integer apoliceId) {
        Apolice apolice = apoliceBean.getApolice(apoliceId);
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
            if (!apolice.getClienteId().equals(id))
                return Response.status(Response.Status.FORBIDDEN).build();

            return Response.ok(apolicesToDTO(apolice, false)).build();
        } else {
            return Response.ok(apolicesToDTO(apolice, true)).build();
        }
    }

    @SuppressWarnings("SameParameterValue")
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

    private ApoliceDTO apolicesToDTO(Apolice apolice, boolean needCliente) {
        ApoliceDTO dto = apolice.toDto();
        Integer seguradoraId = apolice.getSeguradoraId();
        Seguradora seguradora = seguradoraBean.getSeguradora(seguradoraId);
        dto.setSeguradora(seguradora);

        if (needCliente) {
            Integer clienteId = apolice.getClienteId();
            Cliente cliente = clienteBean.getCliente(clienteId);
            dto.setCliente(cliente.toDto());
        }

        return dto;
    }
}
