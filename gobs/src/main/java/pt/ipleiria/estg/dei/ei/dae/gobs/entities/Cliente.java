package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ClienteDTO;

public class Cliente extends UserBase {
    private Integer nif;

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public boolean isColetivo() {
        return !isParticular();
    }

    public boolean isParticular() {
        return nif < 500000000;
    }

    public ClienteDTO toDto() {
        return new ClienteDTO(
                this.getId(),
                this.getNif(),
                this.getNome()
        );
    }
}

