package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.Date;

public class FicheiroDTO {
    private Integer id;
    private String filename;
    private String mimeType;
    private Date criado;

    public FicheiroDTO() {
    }

    public FicheiroDTO(Integer id, String filename, String mimeType, Date criado) {
        this.id = id;
        this.filename = filename;
        this.mimeType = mimeType;
        this.criado = criado;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }
}
