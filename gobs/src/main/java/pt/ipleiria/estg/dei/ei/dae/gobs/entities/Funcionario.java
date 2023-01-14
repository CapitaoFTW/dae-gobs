package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.FuncionarioDTO;

public class Funcionario extends UserBase {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FuncionarioDTO toDTO() {
        return new FuncionarioDTO(
                this.getId(),
                this.getUsername(),
                this.getNome()
        );
    }
}
