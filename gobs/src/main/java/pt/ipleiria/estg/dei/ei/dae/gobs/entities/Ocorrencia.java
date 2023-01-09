package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "existsOcorrencia",
                query = "SELECT COUNT(o.id) FROM Ocorrencia o WHERE o.id = :id"
        ),
        @NamedQuery(
                name = "getOcorrenciasByCliente",
                query = "SELECT o FROM Ocorrencia o WHERE o.clienteId = :clienteId"
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
    private EstadoOcorrencia estadoOcorrencia;

    @UpdateTimestamp
    private Date atualizado;

    @CreationTimestamp
    private Date criado;

    @NotNull
    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.REMOVE)
    private Collection<Ficheiro> ficheiros;

    public Ocorrencia() {
        this.ficheiros = new LinkedHashSet<>();
    }

    public Ocorrencia(Integer clienteId, EstadoOcorrencia estadoOcorrencia) {
        this.clienteId = clienteId;
        this.estadoOcorrencia = estadoOcorrencia;
        this.ficheiros = new LinkedHashSet<>();
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

    public EstadoOcorrencia getEstadoOcorrencia() {
        return estadoOcorrencia;
    }

    public void setEstadoOcorrencia(EstadoOcorrencia estadoOcorrencia) {
        this.estadoOcorrencia = estadoOcorrencia;
    }

    public Date getAtualizado() {
        return atualizado;
    }

    public void setAtualizado(Date atualizado) {
        this.atualizado = atualizado;
    }

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }

    @JsonbTransient//todo
    public Collection<Ficheiro> getFicheiros() {
        return ficheiros;
    }

    public void setFicheiros(Collection<Ficheiro> ficheiros) {
        this.ficheiros = ficheiros;
    }
}
