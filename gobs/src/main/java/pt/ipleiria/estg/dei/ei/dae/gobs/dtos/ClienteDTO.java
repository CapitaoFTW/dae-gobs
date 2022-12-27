package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ClienteAuthDTO;

@SuppressWarnings("unused")
public class ClienteDTO extends ClienteAuthDTO {
    public ClienteDTO() {
    }

    public ClienteDTO(Integer nif, String password) {
        super(nif, password);
    }
}
