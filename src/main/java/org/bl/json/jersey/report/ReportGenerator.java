package org.bl.json.jersey.report;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @author Bogdan Lupashko
 */

public class ReportGenerator {
    private final String TEMPLATE = "HtmlReportTemplate.vm";
    private static final String DESTINATION_FILE_NAME = "API tests run Report";

    public void createHtmlReport(ArrayList<ApiResult> apiResults) {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("responses", apiResults);

        Template template = velocityEngine.getTemplate(TEMPLATE);

        Writer writer = new StringWriter();
        template.merge(velocityContext, writer);

        createHtmlFile(writer.toString());
    }

    private void createHtmlFile(String content) {
        File file = new File(DESTINATION_FILE_NAME + ".html");
        try {
            FileUtils.write(file, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
