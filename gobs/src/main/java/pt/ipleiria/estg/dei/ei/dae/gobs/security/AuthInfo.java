package pt.ipleiria.estg.dei.ei.dae.gobs.security;

public class AuthInfo {
    private final String entityId;
    private final boolean client;
    private final String token;

    public AuthInfo(Integer entityId, String token) {
        this.entityId = Integer.toString(entityId);
        this.client = true;
        this.token = token;
    }

    public AuthInfo(String entityId, String token) {
        this.entityId = entityId;
        this.client = false;
        this.token = token;
    }

    public AuthInfo(String entityId, Boolean isClient, String token) {
        this.entityId = entityId;
        this.client = isClient;
        this.token = token;
    }

    public String getEntityId() {
        return entityId;
    }

    public boolean isClient() {
        return client;
    }

    public String getToken() {
        return token;
    }
}