package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import org.hibernate.annotations.UpdateTimestamp;
import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.OcorrenciaDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "existsOcorrencia",
                query = "SELECT COUNT(o.id) FROM Ocorrencia o WHERE o.id = :id"
        ),
        @NamedQuery(
                name = "getOcorrencias",
                query = "SELECT o FROM Ocorrencia o"
        ),
        @NamedQuery(
                name = "getOcorrenciasByCliente",
                query = "SELECT o FROM Ocorrencia o WHERE o.clienteId = :clienteId"
        ),
        @NamedQuery(
                name = "getOcorrenciasRecentes",
                query = "SELECT o FROM Ocorrencia o order by o.atualizado desc"
        ),
        @NamedQuery(
                name = "getOcorrenciaByClienteRecente",
                query = "SELECT o FROM Ocorrencia o WHERE o.clienteId = :clienteId order by o.atualizado desc"
        )
})
public class Ocorrencia extends EntityId<Integer> {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private Integer clienteId;
    @NotNull
    private Integer apoliceId;
    @NotBlank
    @Size(min = 3)
    private String assunto;
    @NotNull
    private EstadoOcorrencia estadoOcorrencia;
    @NotNull
    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.REMOVE)
    @OrderColumn(name = "criado")
    private Collection<OcorrenciaMensagem> mensagems;
    @UpdateTimestamp
    private Date atualizado;

    public Ocorrencia() {
        this.mensagems = new LinkedHashSet<>();
    }

    public Ocorrencia(Integer clienteId, Integer apoliceId, String assunto) {
        this.clienteId = clienteId;
        this.apoliceId = apoliceId;
        this.assunto = assunto;
        this.estadoOcorrencia = EstadoOcorrencia.Criada;
        this.mensagems = new LinkedHashSet<>();
    }

    public Ocorrencia(Integer clienteId, Integer apoliceId, String assunto, EstadoOcorrencia estadoOcorrencia) {
        this.clienteId = clienteId;
        this.apoliceId = apoliceId;
        this.assunto = assunto;
        this.estadoOcorrencia = estadoOcorrencia;
        this.mensagems = new LinkedHashSet<>();
    }

    @Override
    public Integer getEntityId() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getApoliceId() {
        return apoliceId;
    }

    public void setApoliceId(Integer apoliceId) {
        this.apoliceId = apoliceId;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public EstadoOcorrencia getEstadoOcorrencia() {
        return estadoOcorrencia;
    }

    public void setEstadoOcorrencia(EstadoOcorrencia estadoOcorrencia) {
        this.estadoOcorrencia = estadoOcorrencia;
    }

    public Collection<OcorrenciaMensagem> getMensagems() {
        return mensagems;
    }

    public void setMensagems(Collection<OcorrenciaMensagem> mensagems) {
        this.mensagems = mensagems;
    }

    public Date getAtualizado() {
        return atualizado;
    }

    public void setAtualizado(Date atualizado) {
        this.atualizado = atualizado;
    }

    public OcorrenciaDTO toDTO() {
        return new OcorrenciaDTO(
                this.getId(),
                this.getAssunto(),
                this.getEstadoOcorrencia().getValue(),
                this.getAtualizado()
        );
    }

    public OcorrenciaDTO toDTOcomMensagens() {
        return new OcorrenciaDTO(
                this.getId(),
                this.getAssunto(),
                this.getEstadoOcorrencia().getValue(),
                this.getMensagems().stream().map(OcorrenciaMensagem::toDTO).collect(Collectors.toList()),
                this.getAtualizado()
        );
    }
}

