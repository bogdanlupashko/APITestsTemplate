package org.bl.json.jersey.client;

/**
 * @author Bogdan Lupashko
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyClient {

    private static final String API_PATH =  "api/";
    private static final String VERSION_API_PATH =  "v1.4/";
    public static final String MAIN_URL =  "http://vegaster.webprv.com/";

    public static final String API_PREAMBLE = API_PATH + VERSION_API_PATH;
    public static Logger LOG = LoggerFactory.getLogger(JerseyClient.class.getName());




}
