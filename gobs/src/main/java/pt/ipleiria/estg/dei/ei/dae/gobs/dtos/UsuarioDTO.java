package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.UsuarioAuthDTO;

@SuppressWarnings("unused")
public class UsuarioDTO extends UsuarioAuthDTO {
    public UsuarioDTO() {
    }

    public UsuarioDTO(String username, String password) {
        super(username, password);
    }
}
