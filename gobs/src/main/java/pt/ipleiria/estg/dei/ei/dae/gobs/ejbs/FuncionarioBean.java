package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.FuncionarioInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.FuncionarioProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Funcionario;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;

@Stateless
public class FuncionarioBean {
    @EJB
    private FuncionarioProxy funcionarioProxy;
    private FuncionarioInterface funcionarioBridge;

    @Inject
    private Logger logger;

    public Collection<Funcionario> getFuncionarios() {
        return wrapRequest(FuncionarioInterface::getFuncionarios);
    }

    public Funcionario getFuncionario(Integer id) {
        return wrapRequest(c -> c.getFuncionario(id));
    }

    public Funcionario getFuncionario(String username) {
        return wrapRequest(clienteInterface -> {
            Collection<Funcionario> funcionarios = clienteInterface.getFuncionarioByUsername(username);
            if (funcionarios.size() > 1) {
                logger.warning(String.format("Detetado mais do que um funcionario com o mesmo username: %s", username));
                return null;
            }

            return funcionarios.stream().findFirst().orElse(null);
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    public Funcionario updateFuncionario(Integer id, Funcionario funcionario) {
        return wrapRequest(c -> c.updateFuncionario(id, funcionario));
    }

    private FuncionarioInterface getBridge() {
        if (funcionarioBridge == null)
            funcionarioBridge = funcionarioProxy.getProxy();

        return funcionarioBridge;
    }

    private <R> R wrapRequest(Function<FuncionarioInterface, R> function) {
        try {
            return function.apply(getBridge());
        } catch (WebApplicationException ex) {
            if (ex.getResponse().getStatusInfo() == Response.Status.INTERNAL_SERVER_ERROR)//MockAPi.io returns error 500 instead of 404
                return null;

            throw ex;
        }
    }
}
