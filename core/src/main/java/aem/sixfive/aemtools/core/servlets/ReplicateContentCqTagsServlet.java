package aem.sixfive.aemtools.core.servlets;

import static org.apache.sling.api.servlets.HttpConstants.METHOD_GET;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import aem.sixfive.aemtools.core.services.ReplicateContentService;

@Component(service = Servlet.class, property = {
        SLING_SERVLET_PATHS + "=" + "/bin/aemtools/replicate/cqtags",
        SLING_SERVLET_METHODS + "=" + METHOD_GET
})
public class ReplicateContentCqTagsServlet extends AbstractServlet {

    @Reference
    private ReplicateContentService replicateContentService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        printJsonResponseResult(response, replicateContentService.replicateCqTags(request.getResourceResolver()));
    }
}
