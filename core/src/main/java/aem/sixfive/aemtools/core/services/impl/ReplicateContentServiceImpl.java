package aem.sixfive.aemtools.core.services.impl;

import static aem.sixfive.aemtools.core.utils.PageUtils.getPagesRecursively;
import static aem.sixfive.aemtools.core.utils.ResourceUtils.getResourcesRecursively;
import static aem.sixfive.aemtools.core.utils.ToolsConstants.CONTENT_CQ_TAGS;
import static aem.sixfive.aemtools.core.utils.ToolsConstants.PRIMARY_TYPE_DAM_ASSET;

import java.util.List;
import java.util.stream.Collectors;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;

import aem.sixfive.aemtools.core.services.ReplicateContentService;

@Component(service = ReplicateContentService.class)
public class ReplicateContentServiceImpl implements ReplicateContentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Reference
    private Replicator replicator;

    @Override
    public void replicateCqTags(final ResourceResolver resourceResolver) {
        try {
            final Resource resource = resourceResolver.getResource(CONTENT_CQ_TAGS);
            final List<Resource> allResources = getResourcesRecursively(resource);
            if (!allResources.isEmpty()) {
                final String[] paths = allResources.stream()
                        .map(Resource::getPath)
                        .toArray(String[]::new);
                final Session session = resourceResolver.adaptTo(Session.class);
                replicator.replicate(session, ReplicationActionType.ACTIVATE, paths, new ReplicationOptions());
            }
        } catch (Exception e) {
            logger.error("Error replicate cq:tags", e);
        }
    }

    @Override
    public void replicateDamAssets(final ResourceResolver resourceResolver, final String path) {
        try {
            final Resource resource = resourceResolver.getResource(path);
            final List<Resource> allResources = getResourcesRecursively(resource);
            final List<Resource> resources = allResources
                    .stream()
                    .filter(r -> r.getResourceType().equals(PRIMARY_TYPE_DAM_ASSET))
                    .collect(Collectors.toList());
            if (!resources.isEmpty()) {
                final String[] paths = resources.stream()
                        .map(Resource::getPath)
                        .toArray(String[]::new);
                final Session session = resourceResolver.adaptTo(Session.class);
                replicator.replicate(session, ReplicationActionType.ACTIVATE, paths, new ReplicationOptions());
            }
        } catch (Exception e) {
            logger.error("Error replicate DAM Assets", e);
        }
    }

    @Override
    public void replicatePages(final ResourceResolver resourceResolver, final String path) {
        try {
            final Resource resource = resourceResolver.getResource(path);
            final List<Resource> pages = getPagesRecursively(resource);
            if (!pages.isEmpty()) {
                final String[] paths = pages.stream()
                        .map(Resource::getPath)
                        .toArray(String[]::new);
                final Session session = resourceResolver.adaptTo(Session.class);
                replicator.replicate(session, ReplicationActionType.ACTIVATE, paths, new ReplicationOptions());
            }
        } catch (Exception e) {
            logger.error("Error replicate pages", e);
        }
    }

}
