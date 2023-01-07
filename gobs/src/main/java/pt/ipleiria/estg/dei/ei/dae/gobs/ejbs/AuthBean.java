package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ChangePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ClienteAuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.ClienteAuth;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.AuthInfo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AuthBean {
    public static final String CLIENTE_ROLE = "Cliente";
    public static final String USUARIO_ROLE = "Usuario";

    @EJB
    private ClienteBean clienteBean;
    @PersistenceContext
    private EntityManager entityManager;

    public String genToken() {
        byte[] tokenInBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        return Encoders.BASE64.encode(tokenInBytes);
    }

    public AuthInfo canLogin(ClienteAuthDTO dto) {
        Cliente cliente = validateByNif(dto.getNif(), dto.getPassword());
        if (cliente == null)
            return null;

        return generateAuthInfo(cliente);
    }

    /*public AuthInfo canLogin(UsuarioAuthDTO dto) throws NoSuchAlgorithmException {
        Usuario user = validateCredentials(dto.getUsername(), dto.getPassword());
        if (user == null)
            return null;

        return generateAuthInfo(user);
    }*/

    public Cliente findClienteById(Integer id) {
        return clienteBean.getCliente(id);
    }

    public Cliente findClienteByNif(Integer nif) {
        return clienteBean.getClienteByNif(nif);
    }

    /*public Usuario find(String username) {
        return entityManager.find(Usuario.class, username);
    }*/

    protected Cliente validateByNif(Integer nif, String password) {
        Cliente cliente = findClienteByNif(nif);
        if (cliente == null) {
            return null;
        }

        return cliente.getPassword().equals(password) ? cliente : null;
    }

    protected Cliente validateById(Integer id, String password) {
        Cliente cliente = findClienteById(id);
        if (cliente == null) {
            return null;
        }

        return cliente.getPassword().equals(password) ? cliente : null;
    }

    /*protected Usuario validateCredentials(String username, String password) throws NoSuchAlgorithmException {
        Usuario user = find(username);
        if (user == null)
            return null;

        String hashedPassword = hasher.hash(password);
        return user.getPassword().equals(hashedPassword) ? user : null;
    }*/

    public String getToken(Integer id) {
        ClienteAuth auth = entityManager.find(ClienteAuth.class, id);
        if (auth == null)
            throw new GobsEntityNotFoundException(id, "Não existe dados de autenticação.");

        return auth.getToken();
    }

    public AuthInfo changePassword(Integer id, ChangePasswordDTO dto) {
        Cliente cliente = validateById(id, dto.getOldPassword());
        if (!changePasswordInternal(cliente, dto))
            return null;

        return generateAuthInfo(cliente);
    }

    /*public AuthInfo changePassword(String username, ChangePasswordDTO dto) throws NoSuchAlgorithmException {
        Usuario user = validateCredentials(username, dto.getOldPassword());
        if (!changePasswordInternal(user, dto))
            return null;

        return generateAuthInfo(user);
    }*/

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean changePasswordInternal(Cliente cliente, ChangePasswordDTO dto) {
        if (cliente == null)
            return false;

        String newPassword = dto.getNewPassword();
        if (dto.getOldPassword().equals(newPassword))
            return false;

        if (!newPassword.equals(dto.getConfirmPassword()))
            return false;

        cliente.setPassword(newPassword);
        clienteBean.updateCliente(cliente.getId(), cliente);
        return true;
    }

    protected AuthInfo generateAuthInfo(Cliente cliente) {
        String token = genToken();

        Integer id = cliente.getId();
        ClienteAuth clienteAuth = entityManager.find(ClienteAuth.class, id);
        if (clienteAuth == null) {
            clienteAuth = new ClienteAuth(id, token);
            entityManager.persist(clienteAuth);
        } else
            clienteAuth.setToken(token);

        return new AuthInfo(id, token);
    }
}
