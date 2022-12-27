package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class BaseAuth<T> extends EntityId<T> {
    @NotBlank
    @Size(min = 3, max = 255)
    private String password;
    private String token;

    public BaseAuth() {
    }

    public BaseAuth(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
