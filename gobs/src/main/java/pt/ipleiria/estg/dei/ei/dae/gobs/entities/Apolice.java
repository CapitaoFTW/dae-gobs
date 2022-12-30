package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.JoinColumn;
import java.math.BigDecimal;
import java.util.Date;

public class Apolice extends EntityId<Integer> {
    private Integer id;
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
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
