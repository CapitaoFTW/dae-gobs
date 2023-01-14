package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ReparadorDTO;

public class Reparador extends UserBase {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ReparadorDTO toDto() {
        return new ReparadorDTO(
                this.getId(),
                this.getUsername(),
                this.getNome()
        );
    }
}
