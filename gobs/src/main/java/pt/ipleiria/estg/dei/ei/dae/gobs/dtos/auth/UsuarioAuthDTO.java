package pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class UsuarioAuthDTO extends BaseAuthDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    public UsuarioAuthDTO() {
    }

    public UsuarioAuthDTO(String username, String password) {
        super(password);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
