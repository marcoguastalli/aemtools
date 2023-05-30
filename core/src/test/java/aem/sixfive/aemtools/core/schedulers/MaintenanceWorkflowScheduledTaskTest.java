/*
 *  Copyright 2018 Adobe Systems Incorporated
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import aem.sixfive.aemtools.core.services.MaintenanceWorkflowService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

@ExtendWith({ MockitoExtension.class, AemContextExtension.class })
class MaintenanceWorkflowScheduledTaskTest {

    AemContext aemContext = new AemContext();
    @Mock
    MaintenanceWorkflowService maintenanceWorkflowService;

    @InjectMocks
    MaintenanceWorkflowScheduledTask maintenanceWorkflowScheduledTask = new MaintenanceWorkflowScheduledTask();

    TestLogger logger = TestLoggerFactory.getTestLogger(maintenanceWorkflowScheduledTask.getClass());

    @BeforeEach
    void setup() {
        aemContext.registerService(MaintenanceWorkflowService.class, maintenanceWorkflowService);
        TestLoggerFactory.clear();
    }

    @Test
    void run() {
        MaintenanceWorkflowScheduledTask.Config config = mock(MaintenanceWorkflowScheduledTask.Config.class);
        when(config.enabled()).thenReturn(Boolean.TRUE);
        when(config.server_number()).thenReturn(0);
        when(config.number_of_days()).thenReturn(2);

        maintenanceWorkflowScheduledTask.activate(config);
        maintenanceWorkflowScheduledTask.run();

        List<LoggingEvent> events = logger.getLoggingEvents();
        assertEquals(1, events.size());
        LoggingEvent event = events.get(0);
        assertEquals(Level.INFO, event.getLevel());
        assertEquals(1, event.getArguments().size());
        assertEquals(0, event.getArguments().get(0));
    }
}
