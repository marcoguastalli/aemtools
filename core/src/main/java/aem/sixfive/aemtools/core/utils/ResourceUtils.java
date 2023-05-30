package aem.sixfive.aemtools.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;

public class ResourceUtils {

    private ResourceUtils() {
        throw new UnsupportedOperationException("Do not instantiate Util class");
    }

    public static List<Resource> getResourcesRecursively(final Resource resource) {
        if (resource == null) {
            return Collections.emptyList();
        }
        final List<Resource> resources = new ArrayList<>();
        // Add the given resource itself to the result list:
        resources.add(resource);
        // Recursively fetch all resources of the given children:
        for (final Resource child : resource.getChildren()) {
            resources.addAll(getResourcesRecursively(child));
        }
        return Collections.unmodifiableList(resources);
    }
}
