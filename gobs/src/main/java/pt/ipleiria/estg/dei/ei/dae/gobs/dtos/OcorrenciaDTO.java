package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.Date;

@SuppressWarnings("unused")
public class OcorrenciaDTO {
    private Integer id;
    private ApoliceDTO apolice;
    private Integer estado;
    private String descricao;
    private Date atualizado;
    private Date criado;

    public OcorrenciaDTO() {
    }

    public OcorrenciaDTO(Integer id, Integer estado, String descricao, Date atualizado, Date criado) {
        this.id = id;
        this.estado = estado;
        this.descricao = descricao;
        this.atualizado = atualizado;
        this.criado = criado;
    }

    public OcorrenciaDTO(Integer id, ApoliceDTO apolice, Integer estado, String descricao, Date atualizado, Date criado) {
        this.id = id;
        this.apolice = apolice;
        this.estado = estado;
        this.descricao = descricao;
        this.atualizado = atualizado;
        this.criado = criado;
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

