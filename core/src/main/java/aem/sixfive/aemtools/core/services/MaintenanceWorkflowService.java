package aem.sixfive.aemtools.core.services;

/** Maintenance Workflow Service */
public interface MaintenanceWorkflowService {

    /** clean the folder /var/workflow/instances/serverX
     * 
     * @param serverNumber the number of X
     * @param numberOfDays number of days to subtract from current date */
    void cleanVarWorkflowInstancesFolder(int serverNumber, int numberOfDays);
}
