package aem.sixfive.aemtools.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aem.sixfive.aemtools.core.services.ResourceResolverProvider;

@Component(service = ResourceResolverProvider.class, immediate = true)
public class ResourceResolverProviderImpl implements ResourceResolverProvider {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public ResourceResolver getResourceResolverForService(final String serviceName) {
        logger.debug("Trying to create service resource resolver for service '{}'", serviceName);
        if (StringUtils.isBlank(serviceName)) {
            logger.warn("Service name is 'null' or empty. Unable to create service resource resolver. Returning 'null'!");
            return null;
        }
        try {
            final Map<String, Object> authenticationInfo = new HashMap<>();
            authenticationInfo.put(ResourceResolverFactory.SUBSERVICE, serviceName);
            return this.resolverFactory.getServiceResourceResolver(authenticationInfo);
        } catch (final Exception e) {
            logger.error("Unable to create service resource resolver!", e);
        }
        return null;
    }
}
