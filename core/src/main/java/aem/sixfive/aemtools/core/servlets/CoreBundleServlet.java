package aem.sixfive.aemtools.core.servlets;

import static aem.sixfive.aemtools.core.Constants.CONTENT_TYPE_HTML;
import static org.apache.sling.api.servlets.HttpConstants.METHOD_GET;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.drew.lang.annotations.NotNull;

/** Servlet used to verify that the bundle is active.
 *
 * '/bin' has to be in the 'Execution Paths' list of the OSGi configuration 'Apache Sling Servlet/Script Resolver and Error Handler' */
@Component(service = Servlet.class, property = {
        SLING_SERVLET_PATHS + "=" + "/bin/aemtools/core-bundle",
        SLING_SERVLET_METHODS + "=" + METHOD_GET
})
public class CoreBundleServlet extends SlingSafeMethodsServlet {

    private static final String MESSAGE = "The bundle 'aemtools.core' is Active";

    @Override
    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(CONTENT_TYPE_HTML);
        PrintWriter out = response.getWriter();
        out.print(MESSAGE);
    }
}
