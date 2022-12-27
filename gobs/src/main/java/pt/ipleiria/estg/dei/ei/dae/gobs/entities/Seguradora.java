package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Seguradora extends EntityId<Integer> {
    @Id
    private Integer id;
    @NotBlank
    private String name;

    public Seguradora() {
    }

    public Seguradora(String name) {
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}