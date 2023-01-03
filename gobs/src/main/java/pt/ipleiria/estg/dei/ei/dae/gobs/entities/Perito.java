package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedHashSet;

/*@Entity
@NamedQueries({
        @NamedQuery(
                name = "existsPerito",
                query = "SELECT COUNT(p.username) FROM Perito p WHERE p.username = :username"
        ),
        @NamedQuery(
                name = "getAllCPeritos",
                query = "SELECT p FROM Perito p ORDER BY p.username ASC"//todo change order
        )
})
public class Perito extends Usuario {
    @NotNull
    @OneToMany(mappedBy = "perito", cascade = CascadeType.REMOVE)
    private Collection<Ocorrencia> ocorrencias;

    public Perito() {
        this.ocorrencias = new LinkedHashSet<>();
    }

    public Perito(String username, String password) {
        super(username, password);
        this.ocorrencias = new LinkedHashSet<>();
    }

    public Collection<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(Collection<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
}*/