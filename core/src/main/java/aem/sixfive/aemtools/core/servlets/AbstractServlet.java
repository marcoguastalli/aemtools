package aem.sixfive.aemtools.core.servlets;

import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aem.sixfive.aemtools.core.servlets.domain.JsonResponseError;
import aem.sixfive.aemtools.core.servlets.domain.JsonResponseErrorFields;
import aem.sixfive.aemtools.core.servlets.domain.JsonResponseErrorMessage;
import aem.sixfive.aemtools.core.servlets.domain.JsonResponseResult;
import aem.sixfive.aemtools.core.utils.JsonJacksonUtils;
import aem.sixfive.aemtools.core.utils.ToolsConstants;

public class AbstractServlet extends SlingSafeMethodsServlet {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractServlet.class);

    /** Create and print JsonResponseResult object with the input Object result value
     *
     * @param response the response
     * @param result the Object that will be printed as json
     * @throws ServletException if something goes wrong */
    protected void printJsonResponseResult(SlingHttpServletResponse response, final Object result) throws ServletException {
        final JsonResponseResult jsonResponseResult = new JsonResponseResult(result);
        final String json = JsonJacksonUtils.createJsonStringFromObject(jsonResponseResult);
        writeJsonAsString(response, HttpServletResponse.SC_OK, json);
    }

    /** Write the input jsonAsString as json
     *
     * @param response the response
     * @param statusCode the http return code
     * @param jsonAsString the json that will be printed
     * @throws ServletException if something goes wrong */
    protected void writeJsonAsString(SlingHttpServletResponse response, final int statusCode, final String jsonAsString)
            throws ServletException {
        try {
            response.setStatus(statusCode);
            response.setContentType(ToolsConstants.CONTENT_TYPE_JSON);
            response.addHeader(ToolsConstants.RESPONSE_HEADER_REFERRER_POLICY,
                    ToolsConstants.RESPONSE_HEADER_REFERRER_POLICY_STRICT_WHEN_CORS);
            response.addHeader(ToolsConstants.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN_KEY,
                    ToolsConstants.RESPONSE_HEADER_ACCESS_CONTROL_ASTERIX);
            PrintWriter out = response.getWriter();
            out.print(jsonAsString);
        } catch (Exception e) {
            LOGGER.error("Error writeJsonAsString: " + e);
            throw new ServletException(e);
        }
    }

    /** Verify the mandatory input request parameter 'paramName', if valid returns the value
     *
     * If is not present create and print a JsonResponseError with a 400 BAD_REQUEST Response
     *
     * @param request the request
     * @param response the response
     * @param paramName the name of the parameter to verify
     * @return an Optional with the value of the paramName
     * @throws ServletException if something goes wrong */
    protected Optional<String> verifyMandatoryRequestParameter(SlingHttpServletRequest request,
            SlingHttpServletResponse response, final String paramName) throws ServletException {
        return verifyMandatoryRequestParameter(request, response, paramName, HttpServletResponse.SC_BAD_REQUEST);
    }

    /** Verify the mandatory input request parameter 'paramName', if valid returns the value
     *
     * If is not present create and print a JsonResponseError with the input statusCode Response
     *
     * @param request the request
     * @param response the response
     * @param paramName the name of the parameter to verify
     * @param statusCode of the Response
     * @return an Optional with the value of the paramName
     * @throws ServletException if something goes wrong */
    protected Optional<String> verifyMandatoryRequestParameter(SlingHttpServletRequest request,
            SlingHttpServletResponse response, final String paramName, final int statusCode) throws ServletException {
        final String paramValue = request.getParameter(paramName);
        if (StringUtils.isBlank(paramValue)) {
            final String errorReason = String.format("The %s parameter is mandatory", paramName);
            LOGGER.error(errorReason);
            final JsonResponseError jsonResponseError = createRestJsonResponseError(ToolsConstants.ERROR_TECHNICAL, errorReason,
                    Boolean.FALSE, Boolean.FALSE, ToolsConstants.ERROR_LABEL, errorReason);
            final String json = JsonJacksonUtils.createJsonStringFromObject(jsonResponseError);
            writeJsonAsString(response, statusCode, json);
            return Optional.empty();
        }
        return Optional.of(paramValue);
    }

    /** Create a JsonResponseError object
     *
     * @param type will be printed in the json
     * @param reason will be printed in the json
     * @param translated will be printed in the json
     * @param fullKey will be printed in the json
     * @param title will be printed in the json
     * @param message will be printed in the json
     * @return a JsonResponseError object or null if something goes wrong */
    public static JsonResponseError createRestJsonResponseError(final String type, final String reason, final boolean translated,
            final boolean fullKey, final String title, final String message) {
        return new JsonResponseError(new JsonResponseErrorFields(type, reason,
                new JsonResponseErrorMessage(translated, fullKey, title, message)));
    }
}
