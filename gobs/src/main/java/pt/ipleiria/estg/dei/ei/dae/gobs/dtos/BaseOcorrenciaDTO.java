package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public abstract class BaseOcorrenciaDTO {
    @NotNull
    private Integer estado;
    @NotBlank
    private String descricaoDeOcorrencia;

    protected BaseOcorrenciaDTO() {
    }

    protected BaseOcorrenciaDTO(Integer estado, String descricaoDeOcorrencia) {
        this.estado = estado;
        this.descricaoDeOcorrencia = descricaoDeOcorrencia;
    }

    protected BaseOcorrenciaDTO(EstadoOcorrencia estadoOcorrencia, String descricaoDeOcorrencia) {
        this.estado = estadoOcorrencia.getValue();
        this.descricaoDeOcorrencia = descricaoDeOcorrencia;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public void setEstado(EstadoOcorrencia estadoOcorrencia) {
        this.estado = estadoOcorrencia.getValue();
    }

    public String getDescricaoDeOcorrencia() {
        return descricaoDeOcorrencia;
    }

    public void setDescricaoDeOcorrencia(String descricaoDeOcorrencia) {
        this.descricaoDeOcorrencia = descricaoDeOcorrencia;
    }
}
