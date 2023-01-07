package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ClienteDTO implements Serializable {
    private Integer id;
    private Integer nif;
    private String nome;

    public ClienteDTO() {
    }

    public ClienteDTO(Integer id, Integer nif, String nome) {
        this.id = id;
        this.nif = nif;
        this.nome = nome;
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
