package aem.sixfive.aemtools.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.apache.sling.api.resource.Resource;

import com.day.cq.wcm.api.NameConstants;
import com.day.crx.JcrConstants;

public class PageUtils {

    private PageUtils() {
        throw new UnsupportedOperationException("Do not instantiate Util class");
    }

    public static final Predicate<Resource> IS_PAGE = (Resource r) -> r.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE)
            .equals(NameConstants.NT_PAGE);

    /** Given an input resource as parent
     * 
     * Fetch all the children recursively and test if the resource is also a Page
     * 
     * all Page(s) are returned in the List
     *
     * @param resource a Resource to start from
     * @return a List of Resource with jcr:primaryType === cq:Page */
    public static List<Resource> getPagesRecursively(final Resource resource) {
        if (resource == null) {
            return Collections.emptyList();
        }
        final List<Resource> resources = new ArrayList<>();
        // Add the given resource itself to the result list only if is page
        if (IS_PAGE.test(resource)) {
            resources.add(resource);
        } else {
            return Collections.emptyList();
        }
        // Recursively fetch all resources of the given children:
        for (final Resource child : resource.getChildren()) {
            if (IS_PAGE.test(child)) {
                resources.addAll(getPagesRecursively(child));
            }
        }
        return Collections.unmodifiableList(resources);
    }
}
