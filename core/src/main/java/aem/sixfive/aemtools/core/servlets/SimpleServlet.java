/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package aem.sixfive.aemtools.core.servlets;

import static aem.sixfive.aemtools.core.utils.ToolsConstants.BLANK_SPACE;
import static com.adobe.dam.print.ids.StringConstants.SLING_RESOURCE_TYPE;
import static com.day.cq.commons.jcr.JcrConstants.JCR_TITLE;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

/** Servlet that returns the jcr:content of a page in txt format, e.g.:
 *
 * http://localhost:4502/content/aemtools/language-masters/en/home-page/jcr:content.txt */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "aemtools/components/page", methods = HttpConstants.METHOD_GET, extensions = "txt")
@ServiceDescription("aemtools page jcr:content txt servlet")
public class SimpleServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource();
        final ValueMap valueMap = resource.getValueMap();
        resp.setContentType("text/plain");
        final String result = String.format("Title: '%s'", valueMap.get(JCR_TITLE, String.class))
                .concat(BLANK_SPACE)
                .concat(String.format("ResourceType: '%s'", valueMap.get(SLING_RESOURCE_TYPE, String.class)));
        resp.getWriter().write(result);
    }
}
