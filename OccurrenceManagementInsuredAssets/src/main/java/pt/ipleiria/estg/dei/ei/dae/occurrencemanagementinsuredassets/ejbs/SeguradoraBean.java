package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ejbs;

import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ejbs.api.SeguradoraInterface;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.ejbs.http.SeguradoraProxy;
import pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.entities.Seguradora;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collection;

@Stateless
public class SeguradoraBean {
    @EJB
    private SeguradoraProxy seguradoraProxy;
    private SeguradoraInterface seguradoraBridge;

    public Collection<Seguradora> getAll() {
        return getBridge().getAll();
    }

    private SeguradoraInterface getBridge() {
        if (seguradoraBridge == null)
            seguradoraBridge = seguradoraProxy.getProxy();

        return seguradoraBridge;
    }
}

