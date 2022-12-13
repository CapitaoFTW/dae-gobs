package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class EntityId {
    @Version
    private int version;

    @JsonbTransient
    protected abstract Object entityId();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EntityId entity = (EntityId) o;
        return entityId().equals(entity.entityId());
    }

    @Override
    public int hashCode() {
        return entityId().hashCode();
    }
}
