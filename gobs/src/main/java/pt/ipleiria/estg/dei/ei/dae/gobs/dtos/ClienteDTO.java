package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.util.List;

import static pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.AuthBean.CLIENTE_ROLE;

@SuppressWarnings("unused")
public class ClienteDTO extends UserDTO {
    private Integer nif;

    public ClienteDTO() {
    }

    public ClienteDTO(Integer id, Integer nif, String nome) {
        super(id, nome, List.of(CLIENTE_ROLE));
        this.nif = nif;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }
}

