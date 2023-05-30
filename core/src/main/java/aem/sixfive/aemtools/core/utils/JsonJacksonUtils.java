package aem.sixfive.aemtools.core.utils;

import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonJacksonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonJacksonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonJacksonUtils() {
        throw new UnsupportedOperationException("Do not instantiate Util class");
    }

    /** Create a JsonNode object from the input string
     *
     * @param jsonAsString the string to be converted to a json
     * @return a JsonNode with the result, or null if something goes wrong */
    public static JsonNode createJsonNodeFromJsonString(final String jsonAsString) {
        try {
            return OBJECT_MAPPER.readTree(jsonAsString);
        } catch (IOException e) {
            LOGGER.error("Error createJsonNodeFromJsonString", e);
            return null;
        }
    }

    /** Create an instance of a the object from the input string and class
     *
     * @param jsonAsString the string to be converted to a json
     * @param clazz the type of the class to be created
     * @return a Serializable instance object */
    public static Object createObjectFromJsonString(final String jsonAsString, final Class clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonAsString, clazz);
        } catch (IOException e) {
            LOGGER.error("Error createObjectFromJsonString", e);
            return null;
        }
    }

    /** Create a String representing the Json of the input Serializable object
     *
     * @param serializable the object to be 'jsoned'
     * @return a String with the json, or null if an exception is thrown */
    public static String createJsonStringFromObject(final Serializable serializable) {
        try {
            return OBJECT_MAPPER.writeValueAsString(serializable);
        } catch (Exception e) {
            LOGGER.error("Error createJsonStringFromObject", e);
            return null;
        }
    }

}
