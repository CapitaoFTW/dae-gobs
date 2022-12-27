package pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AuthDTO implements Serializable {
    @NotBlank
    @Size(min = 3, max = 25)
    private String username;
    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
