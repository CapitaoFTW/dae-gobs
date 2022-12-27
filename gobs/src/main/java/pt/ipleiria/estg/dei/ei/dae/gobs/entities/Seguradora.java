package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Seguradora extends EntityId {
    @Id
    private int id;
    @NotBlank
    private String name;

    public Seguradora() {
    }

    public Seguradora(String name) {
        this.name = name;
    }

    @Override
    protected Object entityId() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}