package pt.ipleiria.estg.dei.ei.dae.gobs.entities.auth;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.EntityId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
        name = "getAuthToken",
        query = "SELECT u.token FROM UserAuth u WHERE u.id = :id AND u.mainRole = :mainRole"
)
public class UserAuth extends EntityId<Integer> {
    @Id
    private Integer id;
    private String token;
    private String mainRole;

    public UserAuth() {
    }

    public UserAuth(Integer id, String token, String mainRole) {
        this.id = id;
        this.token = token;
        this.mainRole = mainRole;
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

    public String getMainRole() {
        return mainRole;
    }

    public void setMainRole(String mainRole) {
        this.mainRole = mainRole;
    }
}