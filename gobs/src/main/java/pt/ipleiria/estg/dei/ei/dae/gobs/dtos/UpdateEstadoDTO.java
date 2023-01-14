package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UpdateEstadoDTO implements Serializable {
    @NotNull
    @Min(1)
    @Max(10)
    private Integer estado;

    public Integer getEstado() {
        return estado;
    }

    @SuppressWarnings("unused")
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
