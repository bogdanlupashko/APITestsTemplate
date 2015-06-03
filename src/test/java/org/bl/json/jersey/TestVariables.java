package org.bl.json.jersey;

import com.google.gson.Gson;
import org.bl.json.jersey.model.auth.AuthLogin;
import org.bl.json.jersey.model.errors.Error;
import org.bl.json.jersey.model.errors.ErrorString;
import org.bl.json.jersey.report.ApiResult;
import org.bl.json.jersey.rest.service.Auth;
import org.bl.json.jersey.test.TestAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;

/**
 * @author Bogdan Lupashko
 */

public class TestVariables {

    public static final String DESCRIPTION_TESTS_HEADER = "<br> <b> Description </b> <br> ";
    public static final String LINK_API_DOC_HEADER = "API doc ";

    public static ArrayList<ApiResult> requestsToReport = new ArrayList<>();

    private final static Logger LOGGER = LoggerFactory.getLogger(TestAuth.class.getName());
    public static String email = "googlecomua@mail.ru";
    public static String password = "qqqqqq";


    static RestClient client;
    static String token;

    public static String getToken() {
        if (token != null) {
            return TestVariables.token;
        } else {
            try {
                Auth service = getClient().proxy(Auth.class);
                AuthLogin response = service.authLogin(email, password);

                TestVariables.reportFiller(TestAuth.docLink, TestAuth.authLoginDescription, response);
                token = response.getToken();

            } catch (WebApplicationException errorsMessage) {
                try {
                    TestVariables.reportFiller(TestAuth.docLink, TestAuth.authLoginDescription, errorsMessage.getResponse().readEntity(Error.class));

                } catch (ProcessingException e) {
                    try {
                        TestVariables.reportFiller(TestAuth.docLink, TestAuth.authLoginDescription, errorsMessage.getResponse().readEntity(ErrorString.class));
                    } catch (ProcessingException e1) {
                        TestVariables.reportFillerStackTrace(TestAuth.docLink, TestAuth.authLoginDescription, errorsMessage.getLocalizedMessage());
                    }
                }
            } catch (ProcessingException pro) {
                TestVariables.reportFillerStackTrace(TestAuth.docLink, TestAuth.authLoginDescription, pro.getLocalizedMessage());
                Assert.fail("Object mapping failed : ", pro.getCause());
            }

            return token;
        }
    }


    public static void reportFiller(String docLink, String description, Object responseJson) throws WebApplicationException {
        ApiResult apiResult = new ApiResult();
        apiResult.setDocLink(docLink);
        apiResult.setDescription(description);
        apiResult.setResponseJson(new Gson().toJson(responseJson));
        apiResult.setStatus(true);
        requestsToReport.add(apiResult);
    }

    public static void reportFillerStackTrace(String docLink, String description, String stackTrace) {
        ApiResult apiResult = new ApiResult();
        apiResult.setDocLink(docLink);
        apiResult.setDescription(description);
        apiResult.setResponseJson(stackTrace);
        apiResult.setStatus(false);
        requestsToReport.add(apiResult);
    }


    public static RestClient getClient() {
        if (TestVariables.client != null) {
            return client;
        } else {
            return new RestClient();
        }
    }

}
