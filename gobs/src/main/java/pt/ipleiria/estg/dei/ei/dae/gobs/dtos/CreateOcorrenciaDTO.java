package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class CreateOcorrenciaDTO {
    @NotNull
    private Integer apoliceId;

    @NotBlank
    @Min(15)
    private String descricao;

    public CreateOcorrenciaDTO() {
    }

    public CreateOcorrenciaDTO(Integer apoliceId, String descricao) {
        this.apoliceId = apoliceId;
        this.descricao = descricao;
    }

    public Integer getApoliceId() {
        return apoliceId;
    }

    public void setApoliceId(Integer apoliceId) {
        this.apoliceId = apoliceId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Ocorrencia toEntity(Integer clienteId) {
        return new Ocorrencia(
                clienteId,
                this.getApoliceId(),
                EstadoOcorrencia.Criada,
                this.getDescricao()
        );
    }
}
