package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class EntityId<T> {
    @Version
    private int version;

    @JsonbTransient
    public abstract T getEntityId();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EntityId<?> entity = (EntityId<?>) o;
        return getEntityId().equals(entity.getEntityId());
    }

    @Override
    public int hashCode() {
        return getEntityId().hashCode();
    }
}
