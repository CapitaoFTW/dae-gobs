package pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class ClienteAuthDTO extends BaseAuthDTO {
    @Min(value = 100000000)
    @Max(value = 999999999)
    @NotNull
    private Integer nif;

    public ClienteAuthDTO() {
    }

    public ClienteAuthDTO(Integer nif, String password) {
        super(password);
        this.nif = nif;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }
}
