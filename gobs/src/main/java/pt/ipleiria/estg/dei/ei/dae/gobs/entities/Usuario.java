package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.UsuarioDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings("unused")
public class Usuario extends BaseAuth<String> {
    @Id
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    public Usuario() {
    }

    public Usuario(String username, String password) {
        super(password);
        this.username = username;
    }

    @Override
    public String getEntityId() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UsuarioDTO toDto() {
        return new UsuarioDTO(
                this.getUsername(),
                this.getPassword()
        );
    }
}
