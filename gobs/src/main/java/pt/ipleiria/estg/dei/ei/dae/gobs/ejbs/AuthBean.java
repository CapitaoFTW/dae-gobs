package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ChangePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ClienteAuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.UsuarioAuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.BaseAuth;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Usuario;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.AuthInfo;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;

@Stateless
public class AuthBean {
    public static final String CLIENTE_ROLE = "Cliente";
    public static final String USUARIO_ROLE = "Usuario";
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public String genToken() {
        byte[] tokenInBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        return Encoders.BASE64.encode(tokenInBytes);
    }

    public AuthInfo canLogin(ClienteAuthDTO dto) throws NoSuchAlgorithmException {
        Cliente cliente = validateCredentials(dto.getNif(), dto.getPassword());
        if (cliente == null)
            return null;

        return generateAuthInfo(cliente);
    }

    public AuthInfo canLogin(UsuarioAuthDTO dto) throws NoSuchAlgorithmException {
        Usuario user = validateCredentials(dto.getUsername(), dto.getPassword());
        if (user == null)
            return null;

        return generateAuthInfo(user);
    }

    public Cliente find(Integer nif) {
        return entityManager.find(Cliente.class, nif);
    }

    public Usuario find(String username) {
        return entityManager.find(Usuario.class, username);
    }

    protected Cliente validateCredentials(Integer nif, String password) throws NoSuchAlgorithmException {
        Cliente cliente = find(nif);
        if (cliente == null)
            return null;

        String hashedPassword = hasher.hash(password);
        return cliente.getPassword().equals(hashedPassword) ? cliente : null;
    }

    protected Usuario validateCredentials(String username, String password) throws NoSuchAlgorithmException {
        Usuario user = find(username);
        if (user == null)
            return null;

        String hashedPassword = hasher.hash(password);
        return user.getPassword().equals(hashedPassword) ? user : null;
    }

    public AuthInfo changePassword(Integer nif, ChangePasswordDTO dto) throws NoSuchAlgorithmException {
        Cliente cliente = validateCredentials(nif, dto.getOldPassword());
        if (!changePasswordInternal(cliente, dto))
            return null;

        return generateAuthInfo(cliente);
    }

    public AuthInfo changePassword(String username, ChangePasswordDTO dto) throws NoSuchAlgorithmException {
        Usuario user = validateCredentials(username, dto.getOldPassword());
        if (!changePasswordInternal(user, dto))
            return null;

        return generateAuthInfo(user);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean changePasswordInternal(BaseAuth<?> auth, ChangePasswordDTO dto) throws NoSuchAlgorithmException {
        if (auth == null)
            return false;

        String newPassword = dto.getNewPassword();
        if (dto.getOldPassword().equals(newPassword))
            return false;

        if (!newPassword.equals(dto.getConfirmPassword()))
            return false;

        String hashedPassword = hasher.hash(newPassword);
        auth.setPassword(hashedPassword);
        return true;
    }

    protected AuthInfo generateAuthInfo(Cliente cliente) {
        String token = genToken();
        cliente.setToken(token);
        return new AuthInfo(cliente.getEntityId(), token);
    }

    protected AuthInfo generateAuthInfo(Usuario user) {
        String token = genToken();
        user.setToken(token);
        return new AuthInfo(user.getEntityId(), token);
    }
}
