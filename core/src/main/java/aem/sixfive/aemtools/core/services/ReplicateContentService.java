package aem.sixfive.aemtools.core.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

/** Replicate Content Service */
public interface ReplicateContentService {

    /** Replicate the cq:tags
     *
     * @param resourceResolver the ResourceResolver
     * @return a List with the replicated paths */
    List<String> replicateCqTags(ResourceResolver resourceResolver);

    /** Replicate the DAM Asset and children dam:Asset
     *
     * 
     * @param resourceResolver the ResourceResolver
     * @param path of a DAM Asset
     * @return a List with the replicated paths */
    List<String> replicateDamAssets(ResourceResolver resourceResolver, String path);

    /** Replicate the page and subpages at the input path
     * 
     * @param resourceResolver the ResourceResolver
     * @param path of a Page
     * @return a List with the replicated paths */
    List<String> replicatePages(ResourceResolver resourceResolver, String path);

}