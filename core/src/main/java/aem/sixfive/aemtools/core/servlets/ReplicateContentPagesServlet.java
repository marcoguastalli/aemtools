package aem.sixfive.aemtools.core.servlets;

import static aem.sixfive.aemtools.core.utils.ToolsConstants.DONE;
import static org.apache.sling.api.servlets.HttpConstants.METHOD_GET;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aem.sixfive.aemtools.core.services.ReplicateContentService;
import aem.sixfive.aemtools.core.utils.ToolsConstants;

@Component(service = Servlet.class, property = {
        SLING_SERVLET_PATHS + "=" + "/bin/aemtools/replicate/pages",
        SLING_SERVLET_METHODS + "=" + METHOD_GET
})
public class ReplicateContentPagesServlet extends AbstractServlet {

    @Reference
    private ReplicateContentService replicateContentService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        final Optional<String> path = verifyMandatoryRequestParameter(request, response, ToolsConstants.REQ_PARAM_PATH);
        if (!path.isPresent()) {
            return;
        }

        replicateContentService.replicatePages(request.getResourceResolver(), path.get());
        printJsonResponseResult(response, DONE);
    }
}
