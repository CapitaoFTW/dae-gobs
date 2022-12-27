package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.SeguradoraInterface;

import javax.ejb.Singleton;

@Singleton
public class SeguradoraProxy extends ResteasyProxy<SeguradoraInterface> {
    public SeguradoraProxy() {
        super(SeguradoraInterface.class);
    }
}
