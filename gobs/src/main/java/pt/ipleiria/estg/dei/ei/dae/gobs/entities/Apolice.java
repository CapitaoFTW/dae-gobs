package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Apolice extends EntityId<Integer> {
    private Integer id;
    private Integer seguradoraId;
    private Integer clienteId;
    private String bem;
    private BigDecimal premio;
    private Date prazo;

    @Override
    public Integer getEntityId() {
        return id;
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
}
