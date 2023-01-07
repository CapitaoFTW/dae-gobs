package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClienteAuth extends EntityId<Integer> {
    @Id
    private Integer id;
    private String token;

    public ClienteAuth() {
    }

    public ClienteAuth(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    @Override
    public Integer getEntityId() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
