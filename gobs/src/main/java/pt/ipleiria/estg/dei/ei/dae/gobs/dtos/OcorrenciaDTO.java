package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;

import java.util.Date;

@SuppressWarnings("unused")
public class OcorrenciaDTO {
    private Integer id;
    private ClienteDTO cliente;
    private ApoliceDTO apolice;
    private Integer estado;
    private String descricaoDeOcorrencia;
    private Date atualizado;
    private Date criado;

    public OcorrenciaDTO() {
    }

    public OcorrenciaDTO(Integer id, EstadoOcorrencia estadoOcorrencia, String descricaoDeOcorrencia, Date atualizado, Date criado) {
        this.id = id;
        this.estado = estadoOcorrencia.getValue();
        this.descricaoDeOcorrencia = descricaoDeOcorrencia;
        this.atualizado = atualizado;
        this.criado = criado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
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

    public void setEstado(EstadoOcorrencia estadoOcorrencia) {
        this.estado = estadoOcorrencia.getValue();
    }

    public String getDescricaoDeOcorrencia() {
        return descricaoDeOcorrencia;
    }

    public void setDescricaoDeOcorrencia(String descricaoDeOcorrencia) {
        this.descricaoDeOcorrencia = descricaoDeOcorrencia;
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
