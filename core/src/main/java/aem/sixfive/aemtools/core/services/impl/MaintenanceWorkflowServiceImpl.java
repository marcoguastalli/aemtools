package aem.sixfive.aemtools.core.services.impl;

import static com.day.cq.commons.jcr.JcrConstants.JCR_CREATED;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.serviceusermapping.ServiceUserMapped;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.lang.annotations.NotNull;

import aem.sixfive.aemtools.core.services.MaintenanceWorkflowService;
import aem.sixfive.aemtools.core.services.ResourceResolverProvider;

@Component(service = MaintenanceWorkflowService.class)
public class MaintenanceWorkflowServiceImpl implements MaintenanceWorkflowService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String VAR_WORKFLOW_INSTANCES_SERVER = "/var/workflow/instances/server%s";

    private static final String SERVICE_USER = "aemtoolsUserService";

    // Using this reference the system guarantee that the component will be activated only when all user mappings are done
    @Reference(target = "(subServiceName=" + SERVICE_USER + ")")
    private ServiceUserMapped serviceUserMapped;
    @Reference
    private ResourceResolverProvider resourceResolverProvider;

    private ResourceResolver getResourceResolver() {
        return resourceResolverProvider.getResourceResolverForService(SERVICE_USER);
    }

    @Override
    public synchronized void cleanVarWorkflowInstancesFolder(@NotNull final int serverNumber, @NotNull final int numberOfDays) {
        final String path = String.format(VAR_WORKFLOW_INSTANCES_SERVER, serverNumber);
        try (ResourceResolver resourceResolver = getResourceResolver()) {
            if (null != resourceResolver) {
                logger.info(String.format("Cleaning path '%s' ", path));
                final Resource resource = resourceResolver.getResource(path);
                if (null != resource && resource.hasChildren()) {
                    for (final Resource child : resource.getChildren()) {
                        final Date jcrCreated = child.getValueMap().get(JCR_CREATED, Date.class);
                        final Date nDaysAgo = DateUtils.addDays(new Date(), numberOfDays);
                        if (null != jcrCreated && jcrCreated.before(nDaysAgo)) {
                            logger.info(String.format(" - deleting path '%s' ", child.getPath()));
                            resourceResolver.delete(child);
                        }
                    }
                }
                // save
                if (resourceResolver.hasChanges()) {
                    resourceResolver.commit();
                }
            }
        } catch (Exception e) {
            logger.error(String.format("Error Cleaning path '%s'", path), e);
        }
    }

}
