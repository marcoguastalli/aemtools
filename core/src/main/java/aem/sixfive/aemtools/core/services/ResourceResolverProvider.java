package aem.sixfive.aemtools.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface ResourceResolverProvider {

    ResourceResolver getResourceResolverForService(final String serviceName);
}
