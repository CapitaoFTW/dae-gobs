package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities;

import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.api.TipoDeBem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Apolice {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Cliente cliente;

    @NotNull
    private TipoDeBem tipoDeBem;

    @NotNull
    private String descricao;

    @NotNull
    private Collection<Ocorrecia> ocorrecias;
}
