package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ClienteDTO;

public class Cliente extends UserBase {
    private Integer nif;
    private String email;

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isColetivo() {
        return !isParticular();
    }

    public boolean isParticular() {
        return nif < 500000000;
    }

    public ClienteDTO toDTO() {
        return new ClienteDTO(
                this.getId(),
                this.getNif(),
                this.getNome(),
                this.getEmail()
        );
    }
}

