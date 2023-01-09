package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class UserDTO implements Serializable {
    private Integer id;
    private String nome;
    private Collection<String> roles;

    public UserDTO() {
        this.roles = new LinkedHashSet<>();
    }

    public UserDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
        this.roles = new LinkedHashSet<>();
    }

    public UserDTO(Integer id, String nome, Collection<String> roles) {
        this.id = id;
        this.nome = nome;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }
}
