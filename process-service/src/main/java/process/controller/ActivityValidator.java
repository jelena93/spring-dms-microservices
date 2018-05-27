package process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import process.command.ActivityCmd;
import process.domain.Activity;
import process.domain.Process;
import process.service.ActivityService;
import process.service.ProcessService;

import java.util.List;

@Component
public class ActivityValidator {
    private final ActivityService activityService;
    private final ProcessService processService;

    @Autowired
    public ActivityValidator(ActivityService activityService, ProcessService processService) {
        this.activityService = activityService;
        this.processService = processService;
    }

    public void validate(ActivityCmd activityCmd) throws Exception {
        Process process = processService.findOne(activityCmd.getProcessId());

        List<Activity> byName = activityService.findByNameAndProcessOwnerId(activityCmd.getName(), process.getOwnerId());
        if (!byName.isEmpty()) {
            throw new Exception("Activity with same name already exists");
        }
    }
}
