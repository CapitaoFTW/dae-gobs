package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.Entity;

@Entity
public class ClienteParticular extends Cliente {
    public ClienteParticular() {
    }

    public ClienteParticular(Integer nif, String password) {
        super(nif, password);
    }
}
