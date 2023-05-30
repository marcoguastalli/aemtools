/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package aem.sixfive.aemtools.core.schedulers;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aem.sixfive.aemtools.core.services.MaintenanceWorkflowService;

/** Scheduler used to clean the folder /var/workflow/instances/serverX */
@Designate(ocd = MaintenanceWorkflowScheduledTask.Config.class)
@Component(service = Runnable.class)
public class MaintenanceWorkflowScheduledTask implements Runnable {

    @ObjectClassDefinition(name = "aemtools :: maintenance workflow scheduled task", description = "configuration for maintenance workflow scheduled task")
    public @interface Config {

        @AttributeDefinition(name = "Enabled", description = "Enable or disables the scheduler.")
        boolean enabled() default false;

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "*/30 * * * * ?";

        @AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(name = "Server Number", description = "/var/workflow/instances/serverX where X is the Server Number")
        int server_number() default 0;

        @AttributeDefinition(name = "Number of Days", description = "number of days to subtract from current date")
        int number_of_days() default -7;
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Reference
    private MaintenanceWorkflowService maintenanceWorkflowService;

    private boolean enabled;
    private int serverNumber;
    private int numberOfDays;

    @Activate
    protected void activate(final Config config) {
        enabled = config.enabled();
        serverNumber = config.server_number();
        numberOfDays = config.number_of_days();
    }

    @Override
    public void run() {
        if (enabled) {
            logger.info("MaintenanceWorkflowScheduledTask is now running, serverNumber='{}'", serverNumber);
            maintenanceWorkflowService.cleanVarWorkflowInstancesFolder(serverNumber, numberOfDays);
        }
    }

}
