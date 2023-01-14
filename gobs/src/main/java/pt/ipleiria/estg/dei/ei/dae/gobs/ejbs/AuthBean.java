package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ChangePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.ClienteAuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth.UsuarioAuthDTO;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Funcionario;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.UserBase;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.auth.UserAuth;
import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.GobsEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.gobs.security.AuthInfo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AuthBean {
    public static final String CLIENTE_ROLE = "Cliente";
    public static final String FUNCIONARIO_ROLE = "Funcionario";
    public static final String REPARADOR_ROLE = "Reparador";

    @EJB
    private ClienteBean clienteBean;
    @EJB
    private FuncionarioBean funcionarioBean;
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

    public AuthInfo canLogin(UsuarioAuthDTO dto) {
        Funcionario funcionario = validateByUsername(dto.getUsername(), dto.getPassword());
        if (funcionario == null)
            return null;

        return generateAuthInfo(funcionario);
    }

    public Cliente findClienteById(Integer id) {
        return clienteBean.getCliente(id);
    }

    public Funcionario findFuncionarioById(Integer id) {
        return funcionarioBean.getFuncionario(id);
    }

    public Cliente findByNif(Integer nif) {
        return clienteBean.getClienteByNif(nif);
    }

    public Funcionario findByUsername(String username) {
        return funcionarioBean.getFuncionario(username);
    }

    protected Cliente validateByNif(Integer nif, String password) {
        Cliente cliente = findByNif(nif);
        if (cliente == null) {
            return null;
        }

        return cliente.getPassword().equals(password) ? cliente : null;
    }

    protected Funcionario validateByUsername(String username, String password) {
        Funcionario funcionario = findByUsername(username);
        if (funcionario == null) {
            return null;
        }

        return funcionario.getPassword().equals(password) ? funcionario : null;
    }

    protected Cliente validateClienteById(Integer id, String password) {
        Cliente cliente = findClienteById(id);
        if (cliente == null) {
            return null;
        }

        return cliente.getPassword().equals(password) ? cliente : null;
    }

    protected Funcionario validateFuncionarioById(Integer id, String password) {
        Funcionario funcionario = findFuncionarioById(id);
        if (funcionario == null) {
            return null;
        }

        return funcionario.getPassword().equals(password) ? funcionario : null;
    }

    public String getToken(Integer id, String mainRole) {
        String token = entityManager
                .createNamedQuery("getAuthToken", String.class)
                .setParameter("id", id)
                .setParameter("mainRole", mainRole)
                .getSingleResult();
        if (token == null)
            throw new GobsEntityNotFoundException(id, "Não existe dados de autenticação.");

        return token;
    }

    public AuthInfo changeClientePassword(Integer id, ChangePasswordDTO dto) {
        Cliente cliente = validateClienteById(id, dto.getOldPassword());
        if (!changePasswordInternal(cliente, dto))
            return null;

        clienteBean.updateCliente(cliente.getId(), cliente);
        return generateAuthInfo(cliente);
    }

    public AuthInfo changeFuncionarioPassword(Integer id, ChangePasswordDTO dto) {
        Funcionario funcionario = validateFuncionarioById(id, dto.getOldPassword());
        if (!changePasswordInternal(funcionario, dto))
            return null;

        funcionarioBean.updateFuncionario(funcionario.getId(), funcionario);
        return generateAuthInfo(funcionario);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean changePasswordInternal(UserBase user, ChangePasswordDTO dto) {
        if (user == null)
            return false;

        String newPassword = dto.getNewPassword();
        if (dto.getOldPassword().equals(newPassword))
            return false;

        if (!newPassword.equals(dto.getConfirmPassword()))
            return false;

        user.setPassword(newPassword);
        return true;
    }

    private AuthInfo generateAuthInfo(Cliente cliente) {
        return generateAuthInfoInternal(cliente, CLIENTE_ROLE);
    }

    private AuthInfo generateAuthInfo(Funcionario funcionario) {
        return generateAuthInfoInternal(funcionario, FUNCIONARIO_ROLE);
    }

    protected AuthInfo generateAuthInfoInternal(UserBase funcionario, String mainRole) {
        String token = genToken();

        Integer id = funcionario.getId();
        UserAuth userAuth = entityManager.find(UserAuth.class, new UserAuth(id, mainRole));
        if (userAuth == null) {
            userAuth = new UserAuth(id, token, mainRole);
            entityManager.persist(userAuth);
        } else
            userAuth.setToken(token);

        return new AuthInfo(id.toString(), token, mainRole);
    }
}
