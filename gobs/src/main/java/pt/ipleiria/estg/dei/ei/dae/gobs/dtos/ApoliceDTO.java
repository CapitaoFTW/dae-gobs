package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import java.math.BigDecimal;
import java.util.Date;

public class ApoliceDTO {
    private Integer id;
    private Seguradora seguradora;//todo seguradora dto
    private ClienteDTO cliente;
    private String bem;
    private BigDecimal premio;
    private Date prazo;

    public ApoliceDTO() {
    }

    public ApoliceDTO(Integer id, String bem, BigDecimal premio, Date prazo) {
        this.id = id;
        this.bem = bem;
        this.premio = premio;
        this.prazo = prazo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seguradora getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
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
}
