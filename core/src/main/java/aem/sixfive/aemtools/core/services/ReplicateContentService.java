package aem.sixfive.aemtools.core.services;

import org.apache.sling.api.resource.ResourceResolver;

/** Replicate Content Service */
public interface ReplicateContentService {

    /** Replicate the cq:tags
     *
     *
     * @param resourceResolver the ResourceResolver */
    void replicateCqTags(ResourceResolver resourceResolver);

    /** Replicate the DAM Asset and children dam:Asset
     *
     *
     * @param resourceResolver the ResourceResolver
     * @param path of a DAM Asset */
    void replicateDamAssets(ResourceResolver resourceResolver, String path);

    /** Replicate the page and subpages at the input path
     * 
     * 
     * @param resourceResolver the ResourceResolver
     * @param path of a Page */
    void replicatePages(ResourceResolver resourceResolver, String path);

}