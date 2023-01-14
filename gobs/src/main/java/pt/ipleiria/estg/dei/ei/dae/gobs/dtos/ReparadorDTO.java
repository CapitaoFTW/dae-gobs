package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.List;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.REPARADOR_ROLE;

@SuppressWarnings("unused")
public class ReparadorDTO extends UserDTO {
    private String username;

    public ReparadorDTO() {
    }

    public ReparadorDTO(Integer id, String username, String nome) {
        super(id, nome, List.of(REPARADOR_ROLE));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
