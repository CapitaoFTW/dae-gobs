package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs.http;

import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ClientHttpEngineBuilder43;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

public abstract class ResteasyProxy<T> {
    private static final String MOCKAPI = "https://639ac6ded5141501973ea7c6.mockapi.io/api/V1/";
    private static final ResteasyWebTarget target = getHttpClient();
    private final T proxy;

    public ResteasyProxy(Class<T> proxyType) {
        this.proxy = target.proxy(proxyType);
    }

    private static ResteasyWebTarget getHttpClient() {
        ResteasyClientBuilder clientBuilder = new ResteasyClientBuilderImpl();
        ClientHttpEngine engine = new ClientHttpEngineBuilder43().resteasyClientBuilder(clientBuilder).build();

        //noinspection resource
        ResteasyClient resteasyClient = clientBuilder.httpEngine(engine).connectionPoolSize(25).build();
        return resteasyClient.target(MOCKAPI);
    }

    public T getProxy() {
        return proxy;
    }
}
