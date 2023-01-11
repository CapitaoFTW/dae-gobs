package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.OcorrenciaDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ApoliceBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ClienteBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.OcorrenciaBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("ocorrencias")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
public class OcorrenciaService {
    @EJB
    private ApoliceBean apoliceBean;
    @EJB
    private ClienteBean clienteBean;
    @EJB
    private OcorrenciaBean ocorrenciaBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getOcorrenciasByCliente() {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Ocorrencia> ocorrencias = ocorrenciaBean.findByCliente(id);
        return Response.ok(ocorrenciasToDTOs(ocorrencias, false)).build();
    }

    @GET
    @Path("/recent")
    public Response getOcorrenciaByClienteRecente(@DefaultValue("50") @QueryParam("limit") Integer limit) {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Ocorrencia> ocorrencias = ocorrenciaBean.findByClienteRecente(id, limit);
        return Response.ok(ocorrenciasToDTOs(ocorrencias, false)).build();
    }

    private Collection<OcorrenciaDTO> ocorrenciasToDTOs(Collection<Ocorrencia> ocorrencias, boolean needCliente) {
        Collection<OcorrenciaDTO> ocorrenciaDTOs = new LinkedList<>();
        Map<Integer, Apolice> apolices = new LinkedHashMap<>();
        Map<Integer, Cliente> clientes = new LinkedHashMap<>();

        for (Ocorrencia ocorrencia : ocorrencias) {
            OcorrenciaDTO dto = ocorrencia.toDTO();
            ocorrenciaDTOs.add(dto);

            Integer apoliceId = ocorrencia.getApoliceId();
            Apolice apolice = apolices.get(apoliceId);
            if (apolice == null) {
                apolice = apoliceBean.getApolice(apoliceId);
                apolices.put(apoliceId, apolice);
            }
            dto.setApolice(apolice.toDto());

            //noinspection DuplicatedCode
            if (needCliente) {
                Integer clienteId = ocorrencia.getClienteId();
                Cliente cliente = clientes.get(clienteId);
                if (cliente == null) {
                    cliente = clienteBean.getCliente(clienteId);
                    clientes.put(clienteId, cliente);
                }
                dto.setCliente(cliente.toDto());
            }
        }

        return ocorrenciaDTOs;
    }

    @POST
    @Path("/")
    public Response create(OcorrenciaDTO ocorrenciaDTO) {
        Ocorrencia newOcorrencia = ocorrenciaBean.find(ocorrenciaDTO.getId());
        ocorrenciaBean.create(newOcorrencia);

        return Response.status(Response.Status.CREATED)
                .entity(newOcorrencia.toDTO())
                .build();
    }
}
