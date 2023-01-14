package pt.ipleiria.estg.dei.ei.dae.gobs.api;

import java.util.HashMap;
import java.util.Map;

public enum EstadoOcorrencia {
    Criada(1),
    AguardarMaisDados(2),
    ParaAnalise(3),
    ParaReparacao(4),
    EmReparacao(5),
    ImpossivelReparar(6),
    Reparado(7),
    EmPagamento(8),
    Concluida(9),
    Invalida(10);

    private static final Map<Integer, EstadoOcorrencia> estadosMap = new HashMap<>();

    static {
        for (EstadoOcorrencia estadoOcorrencia : EstadoOcorrencia.values()) {
            estadosMap.put(estadoOcorrencia.value, estadoOcorrencia);
        }
    }

    private final Integer value;

    EstadoOcorrencia(Integer value) {
        this.value = value;
    }

    public static EstadoOcorrencia fromValue(Integer value) {
        return estadosMap.get(value);
    }

    public int getValue() {
        return value;
    }
}
