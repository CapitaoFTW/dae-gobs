package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.TipoDeBem;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
public class Apolice extends EntityId {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn(name = "cliente_id")
    @ManyToOne
    @NotNull
    private Cliente cliente;
    @NotNull
    private TipoDeBem tipoDeBem;
    @NotNull
    private String descricao;
    @NotNull
    @OneToMany(mappedBy = "apolice", cascade = CascadeType.REMOVE)
    private Collection<Ocorrecia> ocorrecias;

    public Apolice() {
        this.ocorrecias = new LinkedHashSet<>();
    }

    public Apolice(Cliente cliente, TipoDeBem tipoDeBem, String descricao) {
        this();
        this.cliente = cliente;
        this.tipoDeBem = tipoDeBem;
        this.descricao = descricao;
    }

    @Override
    protected Object entityId() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoDeBem getTipoDeBem() {
        return tipoDeBem;
    }

    public void setTipoDeBem(TipoDeBem tipoDeBem) {
        this.tipoDeBem = tipoDeBem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<Ocorrecia> getOcorrecias() {
        return ocorrecias;
    }

    public void setOcorrecias(Collection<Ocorrecia> ocorrecias) {
        this.ocorrecias = ocorrecias;
    }
}
