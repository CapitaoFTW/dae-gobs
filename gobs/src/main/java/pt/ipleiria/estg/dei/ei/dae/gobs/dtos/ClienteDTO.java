package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.List;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@SuppressWarnings("unused")
public class ClienteDTO extends UserDTO {
    private Integer nif;
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Integer id, Integer nif, String nome, String email) {
        super(id, nome, List.of(CLIENTE_ROLE));
        this.nif = nif;
        this.email = email;
    }

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
}

