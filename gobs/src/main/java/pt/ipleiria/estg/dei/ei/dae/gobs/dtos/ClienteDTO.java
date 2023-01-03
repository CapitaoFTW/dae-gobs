package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ClienteAuthDTO;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ClienteDTO implements Serializable {
    private Integer nif;
    private String nome;

    public ClienteDTO() {
    }

    public ClienteDTO(Integer nif, String nome) {
        this.nif = nif;
        this.nome = nome;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
