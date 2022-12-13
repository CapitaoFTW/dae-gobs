package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Ficheiro extends EntityId {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String filename;
    @NotBlank
    private String filepath;
    @JoinColumn(name = "ocorrecia_id")
    @ManyToOne
    @NotNull
    private Ocorrecia ocorrecia;

    public Ficheiro() {
    }

    public Ficheiro(String filename, String filepath, Ocorrecia ocorrecia) {
        this.filename = filename;
        this.filepath = filepath;
        this.ocorrecia = ocorrecia;
    }

    @Override
    protected Object entityId() {
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

    public Ocorrecia getOcorrecia() {
        return ocorrecia;
    }

    public void setOcorrecia(Ocorrecia ocorrecia) {
        this.ocorrecia = ocorrecia;
    }
}
