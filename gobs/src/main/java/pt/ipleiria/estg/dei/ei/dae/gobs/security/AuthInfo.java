package pt.ipleiria.estg.dei.ei.dae.gobs.security;

public class AuthInfo {
    private final String entityId;
    private final String token;
    private final String mainRole;

    public AuthInfo(String entityId, String token, String mainRole) {
        this.entityId = entityId;
        this.token = token;
        this.mainRole = mainRole;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getToken() {
        return token;
    }

    public String getMainRole() {
        return mainRole;
    }
}