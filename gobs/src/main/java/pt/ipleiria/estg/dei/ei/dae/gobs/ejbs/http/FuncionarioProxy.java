package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http;

import pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.api.FuncionarioInterface;

import javax.ejb.Singleton;

@Singleton
public class FuncionarioProxy extends ResteasyProxy<FuncionarioInterface> {
    public FuncionarioProxy() {
        super(FuncionarioInterface.class);
    }
}
