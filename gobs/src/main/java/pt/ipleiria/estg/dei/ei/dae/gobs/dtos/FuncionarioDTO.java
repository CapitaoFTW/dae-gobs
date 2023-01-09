package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.List;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.FUNCIONARIO_ROLE;

@SuppressWarnings("unused")
public class FuncionarioDTO extends UserDTO {
    private String username;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(Integer id, String username, String nome) {
        super(id, nome, List.of(FUNCIONARIO_ROLE));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
