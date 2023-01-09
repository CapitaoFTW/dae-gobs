package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ClienteInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.ClienteProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Logger;

@Stateless
public class ClienteBean extends ExternalService<ClienteInterface, ClienteProxy> {
    @EJB
    private ClienteProxy proxy;
    @Inject
    private Logger logger;

    @Override
    protected ClienteProxy getProxy() {
        return proxy;
    }

    public Collection<Cliente> getClientes() {
        return wrapRequest(ClienteInterface::getClientes);
    }

    public Cliente getCliente(Integer id) {
        return wrapRequest(c -> c.getCliente(id));
    }

    public Cliente getClienteByNif(Integer nif) {
        return wrapRequest(clienteInterface -> {
            Collection<Cliente> clientes = clienteInterface.getClienteByNif(nif);
            if (clientes.size() > 1) {
                logger.warning(String.format("Detetado mais do que um cliente com o mesmo nif: %d", nif));
                return null;
            }

            return clientes.stream().findFirst().orElse(null);
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    public Cliente updateCliente(Integer id, Cliente cliente) {
        return wrapRequest(c -> c.updateCliente(id, cliente));
    }
}