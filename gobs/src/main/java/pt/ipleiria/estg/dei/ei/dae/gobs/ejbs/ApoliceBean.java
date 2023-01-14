package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ApoliceInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.ExternalService;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.OrderEnum;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.ApoliceProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Logger;

@Stateless
public class ApoliceBean extends ExternalService<ApoliceInterface, ApoliceProxy> {
    @EJB
    private ApoliceProxy proxy;
    @Inject
    private Logger logger;

    @Override
    protected ApoliceProxy getProxy() {
        return proxy;
    }

    public Collection<Apolice> getApolices() {
        return wrapRequest(ApoliceInterface::getApolices);
    }

    public Collection<Apolice> getApolicesRecent(Integer limite) {
        return wrapRequest(c -> c.getApolices("atualizado", OrderEnum.desc, limite, 1));
    }

    public Apolice getApolice(Integer apoliceId) {
        return wrapRequest(b -> b.getApolice(apoliceId));
    }

    public Collection<Apolice> getClienteApolices(Integer id) {
        return wrapRequest(c -> c.getApolices(id));
    }

    public Collection<Apolice> getClienteApolicesRecent(Integer id, Integer limite) {
        return wrapRequest(c -> c.getApolices(id, "atualizado", OrderEnum.desc, limite, 1));
    }

    @SuppressWarnings("UnusedReturnValue")
    public Apolice updateApolice(Integer id, Apolice apolice) {
        return wrapRequest(c -> c.updateApolice(id, apolice));
    }
}
