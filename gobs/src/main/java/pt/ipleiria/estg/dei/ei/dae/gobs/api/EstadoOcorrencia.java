package pt.ipleiria.estg.dei.ei.dae.gobs.api;

public enum EstadoOcorrencia {
    Criada(1),
    ParaAnalise(2),
    AguardarMaisDados(3),
    EmReparacao(4),
    Reparada(5),
    Concluida(6),
    Invalida(7);

    private final Integer value;
    EstadoOcorrencia(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
