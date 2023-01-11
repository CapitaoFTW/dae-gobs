package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateOcorrenciaDTO extends BaseOcorrenciaDTO {
    @NotNull
    private Integer clienteId;
    @NotNull
    private Integer apoliceId;

    public CreateOcorrenciaDTO() {
    }

    public CreateOcorrenciaDTO(Integer clienteId, Integer apoliceId, Integer estado, String descricaoDeOcorrencia) {
        super(estado, descricaoDeOcorrencia);
        this.clienteId = clienteId;
        this.apoliceId = apoliceId;
    }

    public CreateOcorrenciaDTO(Integer clienteId, Integer apoliceId, EstadoOcorrencia estadoOcorrencia, String descricaoDeOcorrencia) {
        super(estadoOcorrencia, descricaoDeOcorrencia);
        this.clienteId = clienteId;
        this.apoliceId = apoliceId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getApoliceId() {
        return apoliceId;
    }

    public void setApoliceId(Integer apoliceId) {
        this.apoliceId = apoliceId;
    }

    public Ocorrencia toEntity() {
        return new Ocorrencia(
                this.getClienteId(),
                this.getApoliceId(),
                EstadoOcorrencia.fromValue(this.getEstado()),
                this.getDescricaoDeOcorrencia()
        );
    }
}
