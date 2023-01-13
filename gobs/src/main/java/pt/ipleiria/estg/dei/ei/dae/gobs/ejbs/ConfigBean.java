package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.api.EstadoOcorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Apolice;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Ocorrencia;
import pt.ipleiria.estg.dei.ei.dae.gobs.entities.Seguradora;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    private final Random random = new Random();
    @EJB
    private ApoliceBean apoliceBean;
    @EJB
    private ClienteBean clienteBean;
    @EJB
    private OcorrenciaBean ocorrenciaBean;
    @EJB
    private SeguradoraBean seguradoraBean;
    @Inject
    private Logger logger;

    @PostConstruct
    public void postConstruct() {
        try {
            logger.info("Populating DB...");
            populateDB();
            logger.info("Populate Completed!!!");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "!!! Fail to populate DB !!!", ex);
        }
    }

    private void populateDB() {
        //obtem todos os apolices, clientes e seguradoras
        //verifica se as apolices tem dados validos da seguradora e cliente
        //isto e necessario pois estamos a gerar data aleatorio sem controlo no mockapi
        Collection<Apolice> apolices = apoliceBean.getApolices();
        Collection<Cliente> clientes = clienteBean.getClientes();
        Collection<Seguradora> seguradoras = seguradoraBean.getSeguradoras();
        MultivaluedMap<Integer, Integer> clienteApolices = new MultivaluedHashMap<>();

        for (Apolice apolice : apolices) {
            boolean needSave = false;
            Integer clienteId = apolice.getClienteId();
            if (clientes.stream().noneMatch(c -> c.getId().equals(clienteId))) {
                Cliente cliente = randomValue(clientes);
                apolice.setClienteId(cliente.getId());
                needSave = true;
            }

            Integer seguradoraId = apolice.getSeguradoraId();
            if (seguradoras.stream().noneMatch(c -> c.getId().equals(seguradoraId))) {
                Seguradora seguradora = randomValue(seguradoras);
                apolice.setSeguradoraId(seguradora.getId());
                needSave = true;
            }

            Integer apoliceId = apolice.getId();
            if (needSave)
                apoliceBean.updateApolice(apoliceId, apolice);

            clienteApolices.computeIfAbsent(clienteId, k -> new LinkedList<>()).add(apoliceId);
        }

        //Cria algumas ocorrencias com estados aleatorios
        final EstadoOcorrencia[] estados = EstadoOcorrencia.values();
        for (int i = 0; i < 50; i++) {
            Cliente cliente = randomValue(clientes);
            Integer clienteId = cliente.getId();
            Collection<Integer> ap = clienteApolices.get(clienteId);
            if (ap == null)
                continue;

            Integer apoliceId = randomValue(ap);
            EstadoOcorrencia estadoOcorrencia = estados[random.nextInt(estados.length)];
            Ocorrencia ocorrencia = ocorrenciaBean.create(clienteId, apoliceId, "Item quebrado", "Exemplo de descricao para uma ocorrencia").getLeft();
            ocorrencia.setEstadoOcorrencia(estadoOcorrencia);
        }
    }

    private <T> T randomValue(Collection<T> collection) {
        int skips = random.nextInt(collection.size());
        return collection.stream().skip(skips).findFirst().orElse(null);
    }
}