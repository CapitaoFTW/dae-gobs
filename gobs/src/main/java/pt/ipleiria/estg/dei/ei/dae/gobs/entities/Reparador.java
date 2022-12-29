package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "existsReparador",
                query = "SELECT COUNT(r.username) FROM Reparador r WHERE r.username = :username"
        ),
        @NamedQuery(
                name = "getAllReparadores",
                query = "SELECT r FROM Reparador r ORDER BY r.username ASC"//todo change order
        )
})

public class Reparador extends Usuario {
    public Reparador() {

    }

    public Reparador(String username, String password) {
        super(username, password);
    }
}