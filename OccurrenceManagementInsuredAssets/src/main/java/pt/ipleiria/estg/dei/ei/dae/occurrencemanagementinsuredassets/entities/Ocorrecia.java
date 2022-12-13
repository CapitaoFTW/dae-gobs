package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities;

import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.api.EstadoOcorrencia;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Ocorrecia extends EntityId {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Collection<Ficheiro> ficheiros;

    @NotNull
    private EstadoOcorrencia estadoOcorrencia;

    @Override
    protected Object entityId() {
        return id;
    }
}
