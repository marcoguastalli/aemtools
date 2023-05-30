package aem.sixfive.aemtools.core.servlets.domain;

import java.io.Serializable;
import java.util.Objects;

/** Representation of a JSON error result object */
public class JsonResponseError implements Serializable {

    private JsonResponseErrorFields error;

    public JsonResponseError(final JsonResponseErrorFields error) {
        this.error = error;
    }

    public JsonResponseErrorFields getError() {
        return error;
    }

    public void setError(final JsonResponseErrorFields error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonResponseError)) {
            return false;
        }
        JsonResponseError that = (JsonResponseError) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }

    @Override
    public String toString() {
        return "{\"error\":" + this.error + "}";
    }
}
