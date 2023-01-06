package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import org.apache.commons.logging.Log;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ClienteInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.ClienteProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.bind.JsonbException;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;

@Stateless
public class ClienteBean {
    @EJB
    private ClienteProxy clienteProxy;
    private ClienteInterface clienteBridge;

    public Collection<Cliente> getClientes() {
        return wrapRequest(ClienteInterface::getClientes);
    }

    public Cliente getCliente(Integer id) {
        return wrapRequest(b -> b.getCliente(id));
    }

    @SuppressWarnings("UnusedReturnValue")
    public Cliente updateCliente(Integer id, Cliente cliente) {
        return wrapRequest(b -> b.updateCliente(id, cliente));
    }

    public Collection<Apolice> getApolices(Integer id) {
        return wrapRequest(b -> b.getApolices(id));
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
