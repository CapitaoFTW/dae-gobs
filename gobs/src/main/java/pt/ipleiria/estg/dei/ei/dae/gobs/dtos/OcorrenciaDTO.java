package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

@SuppressWarnings("unused")
public class OcorrenciaDTO {
    private Integer id;
    private Integer apoliceId;
    private Integer clienteId;
    private String assunto;
    private Integer estado;
    private Collection<OcorrenciaMensagemDTO> mensagens;
    private Date atualizado;
    private Date criado;

    public OcorrenciaDTO() {
        this.mensagens = new LinkedHashSet<>();
    }

    public OcorrenciaDTO(Integer id, Integer apoliceId, Integer clienteId, String assunto, Integer estado, Date atualizado, Date criado) {
        this.id = id;
        this.apoliceId = apoliceId;
        this.clienteId = clienteId;
        this.assunto = assunto;
        this.estado = estado;
        this.atualizado = atualizado;
        this.criado = criado;
        this.mensagens = new LinkedHashSet<>();
    }

    public OcorrenciaDTO(Integer id, Integer apoliceId, Integer clienteId, String assunto, Integer estado, Collection<OcorrenciaMensagemDTO> mensagens, Date atualizado, Date criado) {
        this.id = id;
        this.apoliceId = apoliceId;
        this.clienteId = clienteId;
        this.assunto = assunto;
        this.estado = estado;
        this.mensagens = mensagens;
        this.atualizado = atualizado;
        this.criado = criado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApoliceId() {
        return apoliceId;
    }

    public void setApoliceId(Integer apoliceId) {
        this.apoliceId = apoliceId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
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

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }
}

