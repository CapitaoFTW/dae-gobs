package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Ficheiro extends EntityId<Long> {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String filename;
    @NotBlank
    private String filepath;
    @JoinColumn(name = "ocorrencia_id")
    @ManyToOne
    @NotNull
    private Ocorrencia ocorrencia;

    public Ficheiro() {
    }

    public Ficheiro(String filename, String filepath, Ocorrencia ocorrencia) {
        this.filename = filename;
        this.filepath = filepath;
        this.ocorrencia = ocorrencia;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }
}
