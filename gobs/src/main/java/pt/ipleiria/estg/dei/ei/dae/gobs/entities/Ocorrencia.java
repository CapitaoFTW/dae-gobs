package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "existsOcorrencia",
                query = "SELECT COUNT(o.id) FROM Ocorrencia o WHERE o.id = :id"
        ),
        @NamedQuery(
                name = "getAllOcorrenciaByCliente",
                query = "SELECT o FROM Ocorrencia o WHERE o.cliente_id = :cliente_id"
        )
})
public class Ocorrencia extends EntityId<Integer> {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private Integer cliente_id;
    @NotNull
    private EstadoOcorrencia estadoOcorrencia;
    /*@ManyToOne
    private Perito perito;*/
    @NotNull
    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.REMOVE)
    private Collection<Ficheiro> ficheiros;

    public Ocorrencia() {
        this.ficheiros = new LinkedHashSet<>();
    }

    public Ocorrencia(EstadoOcorrencia estadoOcorrencia) {
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

    public EstadoOcorrencia getEstadoOcorrencia() {
        return estadoOcorrencia;
    }

    public void setEstadoOcorrencia(EstadoOcorrencia estadoOcorrencia) {
        this.estadoOcorrencia = estadoOcorrencia;
    }

    /*public Perito getPerito() {
        return perito;
    }

    public void setPerito(Perito perito) {
        this.perito = perito;
    }*/

    public Collection<Ficheiro> getFicheiros() {
        return ficheiros;
    }

    public void setFicheiros(Collection<Ficheiro> ficheiros) {
        this.ficheiros = ficheiros;
    }
}
