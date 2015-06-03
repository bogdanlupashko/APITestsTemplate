package org.bl.json.jersey;

import org.bl.json.jersey.client.JerseyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * @author Bogdan Lupashko
 */
public class RestClient {
    private ResteasyWebTarget target;

    private static final String DEFAULT_ENDPOINT = JerseyClient.MAIN_URL;

    public RestClient() {
        this(DEFAULT_ENDPOINT);
    }

    public RestClient(String endpoint) {

        javax.ws.rs.client.Client client = new ResteasyClientBuilder().build();
        target = (ResteasyWebTarget) client.target(endpoint);

    }

    public <T> T proxy(java.lang.Class<T> clazz) {
        return target.proxy(clazz);
    }
}
