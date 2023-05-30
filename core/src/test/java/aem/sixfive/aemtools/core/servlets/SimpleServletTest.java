/*
 *  Copyright 2018 Adobe Systems Incorporated
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

import static com.adobe.dam.print.ids.StringConstants.SLING_RESOURCE_TYPE;
import static com.day.cq.commons.jcr.JcrConstants.JCR_TITLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class SimpleServletTest {

    private SimpleServlet simpleServlet = new SimpleServlet();

    @Test
    void doGet(AemContext context) throws ServletException, IOException {
        context.build().resource("/content/test", JCR_TITLE, "resource title", SLING_RESOURCE_TYPE, "aemtools/components/page").commit();
        context.currentResource("/content/test");

        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();
        assertThat(request, notNullValue());

        simpleServlet.doGet(request, response);
        assertThat(response.getOutputAsString(), is("Title: 'resource title' ResourceType: 'aemtools/components/page'"));
    }
}
