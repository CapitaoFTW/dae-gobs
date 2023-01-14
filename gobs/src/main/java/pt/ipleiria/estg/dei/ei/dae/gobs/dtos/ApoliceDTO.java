package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("unused")
public class ApoliceDTO {
    private Integer id;
    private Integer seguradoraId;
    private Integer clienteId;
    private String bem;
    private BigDecimal premio;
    private Date prazo;
    private Date criado;

    public ApoliceDTO() {
    }

    public ApoliceDTO(Integer id, Integer seguradoraId, Integer clienteId, String bem, BigDecimal premio, Date prazo, Date criado) {
        this.id = id;
        this.seguradoraId = seguradoraId;
        this.clienteId = clienteId;
        this.bem = bem;
        this.premio = premio;
        this.prazo = prazo;
        this.criado = criado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeguradoraId() {
        return seguradoraId;
    }

    public void setSeguradoraId(Integer seguradoraId) {
        this.seguradoraId = seguradoraId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getBem() {
        return bem;
    }

    public void setBem(String bem) {
        this.bem = bem;
    }

    public BigDecimal getPremio() {
        return premio;
    }

    public void setPremio(BigDecimal premio) {
        this.premio = premio;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }
}
