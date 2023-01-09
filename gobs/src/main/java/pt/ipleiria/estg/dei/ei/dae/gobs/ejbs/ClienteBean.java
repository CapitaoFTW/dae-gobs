package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ClienteInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.OrderEnum;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.ClienteProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;

@Stateless
public class ClienteBean {
    @EJB
    private ClienteProxy clienteProxy;
    private ClienteInterface clienteBridge;

    @Inject
    private Logger logger;

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

    public Collection<Apolice> getApolices(Integer id) {
        return wrapRequest(b -> b.getApolices(id));
    }

    public Collection<Apolice> getApolicesRecent(Integer id, Integer limite) {
        return wrapRequest(b -> b.getApolices(id, "updatedAt", OrderEnum.desc, limite, 1));
    }

    public Apolice getApolice(Integer id, Integer apoliceId) {
        return wrapRequest(b -> b.getApolice(id, apoliceId));
    }

    private ClienteInterface getBridge() {
        if (clienteBridge == null)
            clienteBridge = clienteProxy.getProxy();

        return clienteBridge;
    }

    private <R> R wrapRequest(Function<ClienteInterface, R> function) {
        try {
            return function.apply(getBridge());
        } catch (WebApplicationException ex) {
            if (ex.getResponse().getStatusInfo() == Response.Status.INTERNAL_SERVER_ERROR)//MockAPi.io returns error 500 instead of 404
                return null;

            throw ex;
        }
    }
}

