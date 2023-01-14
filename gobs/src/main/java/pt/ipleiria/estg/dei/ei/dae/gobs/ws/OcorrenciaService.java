package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.NewOcorrenciaMensagemDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.FicheiroBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.OcorrenciaBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.OcorrenciaMensagem;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;
import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("ocorrencias")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE, FUNCIONARIO_ROLE})
public class OcorrenciaService {
    @EJB
    private FicheiroBean ficheiroBean;
    @EJB
    private OcorrenciaBean ocorrenciaBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getOcorrencias() {
        Collection<Ocorrencia> ocorrencias;
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
            ocorrencias = ocorrenciaBean.findByCliente(id);
        } else {
            ocorrencias = ocorrenciaBean.getOcorrencias();
        }

        return Response.ok(ocorrencias.stream().map(Ocorrencia::toDTO).collect(Collectors.toList())).build();
    }

    @GET
    @Path("/recent")
    public Response getOcorrenciasRecentes(@DefaultValue("50") @QueryParam("limit") Integer limit) {
        Collection<Ocorrencia> ocorrencias;
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
            ocorrencias = ocorrenciaBean.findByClienteRecente(id, limit);
        } else {
            ocorrencias = ocorrenciaBean.getOcorrenciasRecentes(limit);
        }

        return Response.ok(ocorrencias.stream().map(Ocorrencia::toDTO).collect(Collectors.toList())).build();
    }

    @GET
    @Path("/estados")
    public Response getEstados() {
        return Response.ok(EstadoOcorrencia.values()).build();
    }

    @GET
    @Path("/{id}")
    @Transactional
    public Response getOcorrencia(@PathParam("id") Integer ocorrenciaId) {
        Ocorrencia ocorrencia = ocorrenciaBean.find(ocorrenciaId);
        if (ocorrencia == null)
            throw new GobsEntityNotFoundException(ocorrenciaId, "Falha ao obter Ocorrência, Ocorrência não existe");

        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
            if (!ocorrencia.getClienteId().equals(id))
                return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(ocorrencia.toDTOcomMensagens()).build();
    }

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path("/")
    @RolesAllowed({CLIENTE_ROLE})
    public Response create(MultipartFormDataInput input) throws IOException {
        Map<String, List<InputPart>> form = input.getFormDataMap();

        Integer apoliceId = form.get("apoliceId").get(0).getBody(Integer.class, Integer.TYPE);
        String assunto = form.get("assunto").get(0).getBodyAsString();
        NewOcorrenciaMensagemDTO mensagemDTO = getMessageDTOFromForm(form, 0);

        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
        Pair<Ocorrencia, OcorrenciaMensagem> pair = ocorrenciaBean.create(id, apoliceId, assunto, mensagemDTO);

        Ocorrencia ocorrencia = pair.getLeft();
        Integer ocorrenciaId = ocorrencia.getId();
        getAndSaveFiles(ocorrenciaId, pair.getRight(), form);

        URI uri = UriBuilder.fromResource(OcorrenciaService.class).path(ocorrenciaId.toString()).build();
        return Response.created(uri).entity(ocorrencia.toDTOcomMensagens()).build();
    }

    @PUT
    @Path("/message/{id}")
    public Response addMessage(@PathParam("id") Integer ocorrenciaId, MultipartFormDataInput input) throws IOException {
        Ocorrencia ocorrencia = ocorrenciaBean.find(ocorrenciaId);
        if (ocorrencia == null)
            throw new GobsEntityNotFoundException(ocorrenciaId, "Falha ao obter Ocorrência, Ocorrência não existe");

        int sender = Integer.parseInt(securityContext.getUserPrincipal().getName());
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            if (!ocorrencia.getClienteId().equals(sender))
                return Response.status(Response.Status.FORBIDDEN).build();

            sender = 0;
        }

        Map<String, List<InputPart>> form = input.getFormDataMap();
        NewOcorrenciaMensagemDTO mensagemDTO = getMessageDTOFromForm(input.getFormDataMap(), sender);
        OcorrenciaMensagem mensagem = ocorrenciaBean.addMessage(ocorrencia, mensagemDTO);

        getAndSaveFiles(ocorrenciaId, mensagem, form);
        return Response.ok(ocorrencia.toDTOcomMensagens()).build();
    }

    private NewOcorrenciaMensagemDTO getMessageDTOFromForm(Map<String, List<InputPart>> form, Integer sender) throws IOException {
        String descricao = form.get("descricao").get(0).getBodyAsString();
        return new NewOcorrenciaMensagemDTO(sender, descricao);
    }

    private void getAndSaveFiles(Integer ocorrenciaId, OcorrenciaMensagem mensagemDTO, Map<String, List<InputPart>> form) throws IOException {
        List<InputPart> files = form.getOrDefault("file", new ArrayList<>());
        files.addAll(form.getOrDefault("files", new ArrayList<>()));

        for (InputPart part : files) {
            String filename = getFilename(part.getHeaders());
            java.nio.file.Path dirPath = Paths.get(System.getProperty("user.home"), "uploads", ocorrenciaId.toString());
            mkdirIfNotExists(dirPath.toFile());

            File file = Paths.get(dirPath.toString(), filename).toFile();
            try (InputStream fileStream = part.getBody(InputStream.class, null)) {
                byte[] bytes = IOUtils.toByteArray(fileStream);
                writeFile(bytes, file);
            }

            ficheiroBean.create(mensagemDTO, filename, file.getPath());
        }

        Ocorrencia ocorrencia = pair.getLeft();
        URI uri = UriBuilder.fromResource(OcorrenciaService.class).path(ocorrencia.getId().toString()).build();
        return Response.created(uri).entity(ocorrenciaDTO(ocorrencia, true)).build();
    }

    /*@PUT
    @Path("{id}")
    public Response update(@PathParam("id") Integer Id, OcorrenciaDTO ocorrenciaDTO) {
        ocorrenciaBean.update(Id, ocorrenciaDTO.getName());
        ocorrenciaDTO = ocorrenciaDTO(ocorrenciaBean.find(ocorrenciaDTO.getId()), false);

        return Response.ok(ocorrenciaDTO).build();
    }*/

    private Collection<OcorrenciaDTO> ocorrenciasToDTOs(Collection<Ocorrencia> ocorrencias, boolean comMensagens) {
        Collection<OcorrenciaDTO> ocorrenciaDTOs = new LinkedList<>();
        Map<Integer, Apolice> apolices = new LinkedHashMap<>();

        for (Ocorrencia ocorrencia : ocorrencias) {
            OcorrenciaDTO dto = comMensagens ? ocorrencia.toDTOcomMensagens() : ocorrencia.toDTO();
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

    @SuppressWarnings("SameParameterValue")
    private OcorrenciaDTO ocorrenciaDTO(Ocorrencia ocorrencia, boolean comMensagens) {
        OcorrenciaDTO dto = comMensagens ? ocorrencia.toDTOcomMensagens() : ocorrencia.toDTO();
        Integer apoliceId = ocorrencia.getApoliceId();
        Apolice apolice = apoliceBean.getApolice(apoliceId);
        dto.setApolice(apolice.toDto());
        return dto;
    }

    private String getFilename(MultivaluedMap<String, String> headers) {
        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if (filename.trim().startsWith("filename")) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

    private void mkdirIfNotExists(File file) {
        if (file.exists())
            return;

        //noinspection ResultOfMethodCallIgnored
        file.mkdirs();
    }

    private void writeFile(byte[] content, File file) throws IOException {
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        try (FileOutputStream fop = new FileOutputStream(file)) {
            fop.write(content);
        }
    }
}
