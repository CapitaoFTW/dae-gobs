package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

@SuppressWarnings("unused")
public class OcorrenciaDTO {
    private Integer id;
    private ApoliceDTO apolice;
    private String assunto;
    private Integer estado;
    private Collection<OcorrenciaMensagemDTO> mensagens;
    private Date atualizado;

    public OcorrenciaDTO() {
        this.mensagens = new LinkedHashSet<>();
    }

    public OcorrenciaDTO(Integer id, String assunto, Integer estado, Date atualizado) {
        this.id = id;
        this.assunto = assunto;
        this.estado = estado;
        this.atualizado = atualizado;
        this.mensagens = new LinkedHashSet<>();
    }

    public OcorrenciaDTO(Integer id, ApoliceDTO apolice, String assunto, Integer estado, Date atualizado) {
        this.id = id;
        this.apolice = apolice;
        this.assunto = assunto;
        this.estado = estado;
        this.atualizado = atualizado;
        this.mensagens = new LinkedHashSet<>();
    }

    public OcorrenciaDTO(Integer id, String assunto, Integer estado, Collection<OcorrenciaMensagemDTO> mensagens, Date atualizado) {
        this.id = id;
        this.assunto = assunto;
        this.estado = estado;
        this.mensagens = mensagens;
        this.atualizado = atualizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ApoliceDTO getApolice() {
        return apolice;
    }

    public void setApolice(ApoliceDTO apolice) {
        this.apolice = apolice;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Collection<OcorrenciaMensagemDTO> getMensagens() {
        return mensagens;
    }

    public void setMensagens(Collection<OcorrenciaMensagemDTO> mensagens) {
        this.mensagens = mensagens;
    }

    public Date getAtualizado() {
        return atualizado;
    }

    public void setAtualizado(Date atualizado) {
        this.atualizado = atualizado;
    }
}

