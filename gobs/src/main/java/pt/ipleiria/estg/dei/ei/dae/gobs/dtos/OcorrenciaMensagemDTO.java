package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

public class OcorrenciaMensagemDTO extends NewOcorrenciaMensagemDTO {
    private Integer id;
    private Collection<FicheiroDTO> ficheiros;
    private Date criado;

    public OcorrenciaMensagemDTO() {
        this.ficheiros = new LinkedHashSet<>();
    }

    public OcorrenciaMensagemDTO(Integer id, Integer sender, String mensagem) {
        super(sender, mensagem);
        this.id = id;
        this.ficheiros = new LinkedHashSet<>();
    }

    public OcorrenciaMensagemDTO(Integer id, Integer sender, String mensagem, Date criado) {
        super(sender, mensagem);
        this.id = id;
        this.criado = criado;
        this.ficheiros = new LinkedHashSet<>();
    }

    public OcorrenciaMensagemDTO(Integer id, Integer sender, String mensagem, Collection<FicheiroDTO> ficheiros, Date criado) {
        super(sender, mensagem);
        this.id = id;
        this.ficheiros = ficheiros;
        this.criado = criado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
