package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

public class OcorrenciaMensagemDTO {
    private Integer id;
    private String mensagem;
    private Collection<FicheiroDTO> ficheiros;
    private Date criado;

    public OcorrenciaMensagemDTO() {
        this.ficheiros = new LinkedHashSet<>();
    }

    public OcorrenciaMensagemDTO(Integer id, String mensagem, Date criado) {
        this.id = id;
        this.mensagem = mensagem;
        this.criado = criado;
        this.ficheiros = new LinkedHashSet<>();
    }

    public OcorrenciaMensagemDTO(Integer id, String mensagem, Collection<FicheiroDTO> ficheiros, Date criado) {
        this.id = id;
        this.mensagem = mensagem;
        this.ficheiros = ficheiros;
        this.criado = criado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Collection<FicheiroDTO> getFicheiros() {
        return ficheiros;
    }

    public void setFicheiros(Collection<FicheiroDTO> ficheiros) {
        this.ficheiros = ficheiros;
    }

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }
}
