package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
public class Perito extends User {
    @NotNull
    @OneToMany(mappedBy = "perito", cascade = CascadeType.REMOVE)
    private Collection<Ocorrecia> ocorrecias;

    public Perito() {
        this.ocorrecias = new LinkedHashSet<>();
    }

    public Collection<Ocorrecia> getOcorrecias() {
        return ocorrecias;
    }

    public void setOcorrecias(Collection<Ocorrecia> ocorrecias) {
        this.ocorrecias = ocorrecias;
    }
}