package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.SeguradoraInterface;
import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http.SeguradoraProxy;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collection;

@Stateless
public class SeguradoraBean extends ExternalService<SeguradoraInterface, SeguradoraProxy> {
    @EJB
    private SeguradoraProxy proxy;

    @Override
    protected SeguradoraProxy getProxy() {
        return proxy;
    }

    public Collection<Seguradora> getSeguradoras() {
        return wrapRequest(SeguradoraInterface::getSeguradoras);
    }

    public Seguradora getSeguradora(Integer seguradoraId) {
        return wrapRequest(b -> b.getSeguradora(seguradoraId));
    }
}