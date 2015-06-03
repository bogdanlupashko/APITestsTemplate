package org.bl.json.jersey.test;

import org.bl.json.jersey.TestVariables;
import org.bl.json.jersey.client.JerseyClient;
import org.bl.json.jersey.model.errors.ErrorString;
import org.bl.json.jersey.model.errors.Error;
import org.bl.json.jersey.report.ReportGenerator;
import org.bl.json.jersey.rest.service.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;

/**
 * @author Bogdan Lupashko
 */


public class TestAuth {
    private static Logger LOGGER = LoggerFactory.getLogger(TestAuth.class.getName());
    public static final String docLink = "http://vegaster.webprv.com/api/doc#auth";
    public static final String authLoginDescription = "Login test";
    private static final String authLogoutDescription = "logout test";

    @Test(description = TestVariables.DESCRIPTION_TESTS_HEADER + authLoginDescription + "<br> <a href=" + docLink + ">" + TestVariables.LINK_API_DOC_HEADER + "</a>")
    public void authLogin() {
        TestVariables.getToken();
        new ReportGenerator().createHtmlReport(TestVariables.requestsToReport);

    }

    @Test(description = TestVariables.DESCRIPTION_TESTS_HEADER + authLogoutDescription + "<br> <a href=" + docLink + ">" + TestVariables.LINK_API_DOC_HEADER + "</a>")
    public void authLogout() {
        try {
            Auth service = TestVariables.getClient().proxy(Auth.class);
            String response = service.authLogout(TestVariables.getToken());
            TestVariables.reportFiller(docLink, authLogoutDescription, response);
            Assert.assertTrue(response.toString().isEmpty());
            JerseyClient.LOG.error(response.toString());
        } catch (WebApplicationException errorsMessage) {
            try {
                TestVariables.reportFiller(TestAuth.docLink, TestAuth.authLogoutDescription, errorsMessage.getResponse().readEntity(Error.class));

            } catch (ProcessingException e) {
                try {
                    TestVariables.reportFiller(TestAuth.docLink, TestAuth.authLogoutDescription, errorsMessage.getResponse().readEntity(ErrorString.class));
                } catch (ProcessingException e1) {
                    TestVariables.reportFillerStackTrace(TestAuth.docLink, TestAuth.authLogoutDescription, errorsMessage.getLocalizedMessage());
                }
            }
        } catch (ProcessingException pro) {
            TestVariables.reportFillerStackTrace(TestAuth.docLink, TestAuth.authLogoutDescription, pro.getLocalizedMessage());
            Assert.fail("Object mapping failed : ", pro.getCause());
        } finally {
            new ReportGenerator().createHtmlReport(TestVariables.requestsToReport);
        }
    }
}

