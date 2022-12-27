package pt.ipleiria.estg.dei.ei.dae.gobs.security;

public class AuthInfo {
    private final String username;
    private final String token;

    public AuthInfo(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}