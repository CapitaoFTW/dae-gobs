package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ExternalService;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.FuncionarioInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.FuncionarioProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Funcionario;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Logger;

@Stateless
public class FuncionarioBean extends ExternalService<FuncionarioInterface, FuncionarioProxy> {
    @EJB
    private FuncionarioProxy proxy;
    @Inject
    private Logger logger;

    @Override
    protected FuncionarioProxy getProxy() {
        return proxy;
    }

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
}
