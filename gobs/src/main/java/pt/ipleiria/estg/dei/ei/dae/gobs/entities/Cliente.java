package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.ClienteDTO;

import javax.json.bind.annotation.JsonbTransient;

public class Cliente {
    private Integer id;
    private Integer nif;
    private String password;
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @JsonbTransient
    public boolean isColetivo() {
        return !isParticular();
    }

    @JsonbTransient
    public boolean isParticular() {
        return nif < 500000000;
    }

    public ClienteDTO toDto() {
        return new ClienteDTO(
                this.getId(),
                this.getNif(),
                this.getNome()
        );
    }
}
