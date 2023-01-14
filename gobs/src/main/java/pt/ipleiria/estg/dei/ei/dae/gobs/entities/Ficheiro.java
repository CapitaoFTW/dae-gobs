package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import org.hibernate.annotations.CreationTimestamp;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.FicheiroDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@SuppressWarnings("unused")
public class Ficheiro extends EntityId<Integer> {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String filename;
    @NotBlank
    private String filepath;
    @JoinColumn(name = "mensagem_id")
    @ManyToOne
    @NotNull
    private OcorrenciaMensagem mensagem;
    @CreationTimestamp
    private Date criado;

    public Ficheiro() {
    }

    public Ficheiro(String filename, String filepath, OcorrenciaMensagem mensagem) {
        this.filename = filename;
        this.filepath = filepath;
        this.mensagem = mensagem;
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

    public OcorrenciaMensagem getOcorrenciaMensagem() {
        return mensagem;
    }

    public void setOcorrenciaMensagem(OcorrenciaMensagem mensagem) {
        this.mensagem = mensagem;
    }

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }

    public FicheiroDTO toDTO() {
        return new FicheiroDTO(
                this.getId(),
                this.getFilename(),
                this.getCriado()
        );
    }
}
