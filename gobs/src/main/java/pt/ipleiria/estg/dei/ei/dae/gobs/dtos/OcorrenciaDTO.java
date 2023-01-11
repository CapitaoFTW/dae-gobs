package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;

import java.util.Date;


public class OcorrenciaDTO {
    private Integer id;
    private ClienteDTO cliente;
    private ApoliceDTO apolice;
    private Integer estado;
    private String descricaoDeOcorrencia;
    private Date atualizado;
    private Date criado;
    protected long clienteId;
    protected long apoliceId;

    public OcorrenciaDTO() {
    }

    public OcorrenciaDTO(Integer id, EstadoOcorrencia estadoOcorrencia, String descricaoDeOcorrencia, Date atualizado, Date criado, long clienteId, long apoliceId) {
        this.id = id;
        this.estado = estadoOcorrencia.getValue();
        this.descricaoDeOcorrencia = descricaoDeOcorrencia;
        this.atualizado = atualizado;
        this.criado = criado;
        this.clienteId = clienteId;
        this.apoliceId = apoliceId;
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
