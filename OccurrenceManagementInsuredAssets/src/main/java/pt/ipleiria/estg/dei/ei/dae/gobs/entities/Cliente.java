package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
public class Cliente extends EntityId {
    @Id
    @Size(min = 9, max = 9)
    private int nif;
    @NotNull
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private Collection<Apolice> apolices;

    public Cliente() {
        this.apolices = new LinkedHashSet<>();
    }

    public Cliente(int nif) {
        this();
        this.nif = nif;
    }

    @Override
    protected Object entityId() {
        return nif;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public Collection<Apolice> getApolices() {
        return apolices;
    }

    public void setApolices(Collection<Apolice> apolices) {
        this.apolices = apolices;
    }

    public boolean isColetivo() {
        return !isParticular();
    }

    public boolean isParticular() {
        return nif < 500000000;
    }
}
