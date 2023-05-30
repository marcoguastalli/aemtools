package aem.sixfive.aemtools.core.utils;

public class ToolsConstants {

    private ToolsConstants() {
        throw new UnsupportedOperationException("Do not instantiate this class");
    }

    public static final String BLANK_SPACE = " ";

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_CQ_TAGS = "/content/cq:tags";

    public static final String REQ_PARAM_PATH = "path";

    public static final String ERROR_LABEL = "Error";
    public static final String ERROR_TITLE = "ERROR";
    public static final String ERROR_TECHNICAL = "ERROR_TECHNICAL";

    // this header makes it possible to control which referrer information is included in requests
    public static final String RESPONSE_HEADER_REFERRER_POLICY = "Referrer-Policy";
    public static final String RESPONSE_HEADER_REFERRER_POLICY_NO_REFERRER = "no-referrer";
    public static final String RESPONSE_HEADER_REFERRER_POLICY_SAME_ORIGIN = "same-origin";
    public static final String RESPONSE_HEADER_REFERRER_POLICY_STRICT_WHEN_CORS = "strict-origin-when-cross-origin";
    // this header makes it possible to control whether and which external pages are permitted access to the resources on a given web server
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN_KEY = "Access-Control-Allow-Origin";
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ASTERIX = "*";
    /** If the server sends a response with an Access-Control-Allow-Origin value that is an explicit origin (rather than the "*" wildcard),
     * then the response should also include a Vary response header with the value Origin — to indicate to browsers that server responses
     * can differ based on the value of the Origin request header. */
    public static final String RESPONSE_HEADER_VARY = "Vary";
    public static final String RESPONSE_HEADER_VARY_ORIGIN = "Origin";
    /** The Access-Control-Allow-Credentials header can also be set in addition to the Access-Control-Allow-Origin header if the content is
     * protected using cookies or the Authorization header. Both headers should only be set specifically for trusted pages using a whitelist
     * approach. */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    /** The only valid value for this header is true (case-sensitive). If you don't need credentials, omit this header entirely (rather than
     * setting its value to false */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS_UNIQUE_VALUE = "true";
    // this header specifies the method or methods allowed when accessing the resource in response to a preflight request
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    public static final String RESPONSE_HEADER_USER_AGENT = "User Agent";
    public static final String PRIMARY_TYPE_CQ_TAGS = "cq:Tag";
    public static final String PRIMARY_TYPE_DAM_ASSET = "dam:Asset";
    public static final String DONE = "done";

}
