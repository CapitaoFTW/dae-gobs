package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ClienteDTO;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "existsCliente",
                query = "SELECT COUNT(c.nif) FROM Cliente c WHERE c.nif = :nif"
        ),
        @NamedQuery(
                name = "getAllClientes",
                query = "SELECT c FROM Cliente c ORDER BY c.nif ASC"//todo change order
        )
})
public class Cliente extends BaseAuth<Integer> {
    @Id
    @Min(value = 100000000)
    @Max(value = 999999999)
    private Integer nif;
    @NotNull
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private Collection<Apolice> apolices;

    public Cliente() {
        this.apolices = new LinkedHashSet<>();
    }

    public Cliente(Integer nif, String password) {
        super(password);
        this.nif = nif;
        this.apolices = new LinkedHashSet<>();
    }

    @Override
    public Integer getEntityId() {
        return nif;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
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

    public ClienteDTO toDto() {
        return new ClienteDTO(
                this.getNif(),
                this.getPassword()
        );
    }
}
