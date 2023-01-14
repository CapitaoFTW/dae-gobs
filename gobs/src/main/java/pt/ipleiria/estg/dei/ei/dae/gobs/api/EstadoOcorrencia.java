package pt.ipleiria.estg.dei.ei.dae.gobs.api;

import java.util.HashMap;
import java.util.Map;

public enum EstadoOcorrencia {
    Criada(0),
    AguardarMaisDados(1),
    ParaAnalise(2),
    ParaReparacao(3),
    EmReparacao(4),
    ImpossivelReparar(5),
    Reparado(6),
    EmPagamento(7),
    Concluida(8),
    Invalida(9);

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


    @Override
    public String toString() {
        switch (this) {
            case Criada:
                return "Criada";
            case AguardarMaisDados:
                return "Dados em falta";
            case ParaAnalise:
                return "Para análise";
            case ParaReparacao:
                return "Para reparação";
            case EmReparacao:
                return "Em reparação";
            case ImpossivelReparar:
                return "Impossivel reparar";
            case Reparado:
                return "Reparado";
            case EmPagamento:
                return "Em pagammento";
            case Concluida:
                return "Concluida";
            case Invalida:
                return "Invalida";
        }

        return super.toString();
    }
}
