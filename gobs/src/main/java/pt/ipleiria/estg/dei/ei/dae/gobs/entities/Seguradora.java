package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

public class Seguradora extends EntityId<Integer> {
    private Integer id;
    private String nome;

    @Override
    public Integer getEntityId() {
        return id;
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
}