package pt.ipleiria.estg.dei.ei.dae.gobs.ws;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.CreateOcorrenciaDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.OcorrenciaDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.ApoliceBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.FicheiroBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.OcorrenciaBean;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.*;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;
import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;

@Authenticated
@Consumes({MediaType.APPLICATION_JSON})
@Path("ocorrencias")
@Produces({MediaType.APPLICATION_JSON})
@RolesAllowed({CLIENTE_ROLE})
public class OcorrenciaService {
    @EJB
    private ApoliceBean apoliceBean;
    @EJB
    private FicheiroBean ficheiroBean;
    @EJB
    private OcorrenciaBean ocorrenciaBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed({FUNCIONARIO_ROLE})
    public Response getOcorrencias() {
        Collection<Ocorrencia> ocorrencias;
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
            ocorrencias = ocorrenciaBean.findByCliente(id);
        }
        else {
            ocorrencias = ocorrenciaBean.getOcorrencias();
        }
        return Response.ok(ocorrenciasToDTOs(ocorrencias)).build();
    }

    @GET
    @Path("/recent")
    @RolesAllowed({FUNCIONARIO_ROLE})
    public Response getOcorrenciasRecentes(@DefaultValue("50") @QueryParam("limit") Integer limit) {
        Collection<Ocorrencia> ocorrencias;
        if (securityContext.isUserInRole(CLIENTE_ROLE)) {
            Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());
            ocorrencias = ocorrenciaBean.findByClienteRecente(id, limit);
        }
        else {
            ocorrencias = ocorrenciaBean.getOcorrencias();
        }

        return Response.ok(ocorrenciasToDTOs(ocorrencias)).build();
    }

    @GET
    @Path("/estados")
    @RolesAllowed({FUNCIONARIO_ROLE})
    public Response getEstados() {
        return Response.ok(EstadoOcorrencia.values()).build();
    }

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path("/")
    public Response create(MultipartFormDataInput input) throws IOException {
        Integer id = Integer.valueOf(securityContext.getUserPrincipal().getName());

        Map<String, List<InputPart>> form = input.getFormDataMap();

        Integer apoliceId = form.get("apoliceId").get(0).getBody(Integer.class, Integer.TYPE);
        String descricao = form.get("descricao").get(0).getBodyAsString();
        CreateOcorrenciaDTO createOcorrenciaDTO = new CreateOcorrenciaDTO(apoliceId, descricao);
        Ocorrencia ocorrencia = ocorrenciaBean.create(id, createOcorrenciaDTO);

        List<InputPart> files = form.getOrDefault("file", new ArrayList<>());
        files.addAll(form.getOrDefault("files", new ArrayList<>()));

        for (InputPart part : files) {
            String filename = getFilename(part.getHeaders());
            java.nio.file.Path dirPath = Paths.get(System.getProperty("user.home"), "uploads", id.toString());
            mkdirIfNotExists(dirPath.toFile());

            File file = Paths.get(dirPath.toString(), filename).toFile();
            try (InputStream fileStream = part.getBody(InputStream.class, null)) {
                byte[] bytes = IOUtils.toByteArray(fileStream);
                writeFile(bytes, file);
            }

            ficheiroBean.create(ocorrencia, filename, file.getPath());
        }

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
