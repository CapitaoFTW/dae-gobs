package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.ClienteParticular;

@SuppressWarnings("unused")
public class ClienteParticularDTO extends ClienteDTO {
    public ClienteParticularDTO() {
    }

    public ClienteParticularDTO(Integer nif, String password) {
        super(nif, password);
    }

    public ClienteParticular toEntity() {
        return new ClienteParticular(
                this.getNif(),
                this.getPassword()
        );
    }
}
