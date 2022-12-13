package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Cliente extends EntityId {
    @Id
    @Size(min = 9, max = 9)
    private int nif;

    @Override
    protected Object entityId() {
        return nif;
    }

    public boolean isColetivo() {
        return !isParticular();
    }

    public boolean isParticular() {
        return nif < 500000000;
    }
}
