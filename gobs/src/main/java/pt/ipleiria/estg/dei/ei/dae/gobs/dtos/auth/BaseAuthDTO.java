package pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("unused")
@MappedSuperclass
public abstract class BaseAuthDTO implements Serializable {
    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    protected BaseAuthDTO() {
    }

    protected BaseAuthDTO(String password) {
        this.password = password;
    }

    @JsonbTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
