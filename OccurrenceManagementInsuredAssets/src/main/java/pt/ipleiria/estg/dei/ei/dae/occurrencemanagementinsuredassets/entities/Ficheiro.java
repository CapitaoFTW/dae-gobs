package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Ficheiro extends EntityId {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String filepath;
    @NotBlank
    private String filename;

    @Override
    protected Object entityId() {
        return id;
    }
}
