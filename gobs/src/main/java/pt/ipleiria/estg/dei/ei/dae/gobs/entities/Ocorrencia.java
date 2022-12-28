package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
public class Ocorrencia extends EntityId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn(name = "apolice_id")
    @ManyToOne
    @NotNull
    private Apolice apolice;
    @NotNull
    private EstadoOcorrencia estadoOcorrencia;
    @ManyToOne
    private Perito perito;
    @NotNull
    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.REMOVE)
    private Collection<Ficheiro> ficheiros;

    public Ocorrencia() {
        this.ficheiros = new LinkedHashSet<>();
    }

    public Ocorrencia(Apolice apolice, EstadoOcorrencia estadoOcorrencia) {
        this();
        this.apolice = apolice;
        this.estadoOcorrencia = estadoOcorrencia;
    }

    @Override
    public Long getEntityId() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apolice getApolice() {
        return apolice;
    }

    public void setApolice(Apolice apolice) {
        this.apolice = apolice;
    }

    public EstadoOcorrencia getEstadoOcorrencia() {
        return estadoOcorrencia;
    }

    public void setEstadoOcorrencia(EstadoOcorrencia estadoOcorrencia) {
        this.estadoOcorrencia = estadoOcorrencia;
    }

    public Perito getPerito() {
        return perito;
    }

    public void setPerito(Perito perito) {
        this.perito = perito;
    }

    public Collection<Ficheiro> getFicheiros() {
        return ficheiros;
    }

    public void setFicheiros(Collection<Ficheiro> ficheiros) {
        this.ficheiros = ficheiros;
    }
}
