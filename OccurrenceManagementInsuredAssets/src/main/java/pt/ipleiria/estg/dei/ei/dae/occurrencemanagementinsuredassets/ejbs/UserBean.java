package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ejbs;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.dtos.auth.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.dtos.auth.ChangePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities.User;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.security.AuthInfo;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public AuthInfo canLogin(AuthDTO dto) throws NoSuchAlgorithmException {
        User user = validateCredentials(dto.getUsername(), dto.getPassword());
        if (user == null)
            return null;

        return generateAuthInfo(user);
    }

    public AuthInfo changePassword(String username, ChangePasswordDTO dto) throws NoSuchAlgorithmException {
        User user = validateCredentials(username, dto.getOldPassword());
        if (user == null)
            return null;

        String newPassword = dto.getNewPassword();
        if (!newPassword.equals(dto.getConfirmPassword()))
            return null;

        String hashedPassword = hasher.hash(newPassword);
        user.setPassword(hashedPassword);
        return generateAuthInfo(user);
    }

    protected AuthInfo generateAuthInfo(User user) {
        byte[] tokenInBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String token = Encoders.BASE64.encode(tokenInBytes);

        user.setToken(token);
        return new AuthInfo(user.getUsername(), token);
    }

    public User find(String username) {
        return entityManager.find(User.class, username);
    }

    public User validateCredentials(String username, String password) throws NoSuchAlgorithmException {
        User user = find(username);
        if (user == null)
            return null;

        String hashedPassword = hasher.hash(password);
        return user.getPassword().equals(hashedPassword) ? user : null;
    }
}
