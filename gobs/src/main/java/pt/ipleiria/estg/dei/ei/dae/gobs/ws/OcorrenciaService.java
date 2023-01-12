package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.CreateOcorrenciaDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.OcorrenciaDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ApoliceBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.OcorrenciaBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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
    private OcorrenciaBean ocorrenciaBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getOcorrenciasByCliente() {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Ocorrencia> ocorrencias = ocorrenciaBean.findByCliente(id);
        return Response.ok(ocorrenciasToDTOs(ocorrencias)).build();
    }

    @GET
    @Path("/recent")
    public Response getOcorrenciaByClienteRecente(@DefaultValue("50") @QueryParam("limit") Integer limit) {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Collection<Ocorrencia> ocorrencias = ocorrenciaBean.findByClienteRecente(id, limit);
        return Response.ok(ocorrenciasToDTOs(ocorrencias)).build();
    }

    @GET
    @Path("/estados")
    public Response getEstados() {
        return Response.ok(EstadoOcorrencia.values()).build();
    }

    @POST
    @Path("/")
    public Response create(@Valid CreateOcorrenciaDTO ocorrenciaDTO) {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Ocorrencia ocorrencia = ocorrenciaBean.create(id, ocorrenciaDTO);
        URI uri = UriBuilder.fromResource(OcorrenciaService.class).path(ocorrencia.getId().toString()).build();
        return Response.created(uri).entity(ocorrenciaDTO(ocorrencia)).build();
    }

    private Collection<OcorrenciaDTO> ocorrenciasToDTOs(Collection<Ocorrencia> ocorrencias) {
        Collection<OcorrenciaDTO> ocorrenciaDTOs = new LinkedList<>();
        Map<Integer, Apolice> apolices = new LinkedHashMap<>();

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
        }

        return ocorrenciaDTOs;
    }

    private OcorrenciaDTO ocorrenciaDTO(Ocorrencia ocorrencia) {
        OcorrenciaDTO dto = ocorrencia.toDTO();
        Integer apoliceId = ocorrencia.getApoliceId();
        Apolice apolice = apoliceBean.getApolice(apoliceId);
        dto.setApolice(apolice.toDto());
        return dto;
    }
}
