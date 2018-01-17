package process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import process.command.ActivityCmd;
import process.command.ProcessCmd;
import process.domain.Activity;
import process.domain.Process;
import process.dto.TreeDto;
import process.mapper.ActivityMapper;
import process.mapper.ProcessMapper;
import process.service.ActivityService;
import process.service.ProcessService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ProcessServiceController {

    @Autowired
    ProcessService processService;
    @Autowired
    ActivityService activityService;
    @Autowired
    ProcessMapper processMapper;
    @Autowired
    ActivityMapper activityMapper;

    @GetMapping(path = "/all/{ownerId}")
    public List<TreeDto> getProcesses(@PathVariable long ownerId) {
        List<Process> processes = processService.findByOwnerId(ownerId);
        List<TreeDto> data = new ArrayList<>();
        for (Process process : processes) {
            TreeDto p;
            String icon;
            icon = TreeDto.PROCESS_ICON;
            if (process.getParent() == null) {
                p = new TreeDto(process.getId(), "#", process.getName(), icon, process.isPrimitive());
            } else {
                p = new TreeDto(process.getId(), process.getParent().getId() + "", process.getName(), icon,
                                process.isPrimitive());
            }
            data.add(p);
            if (process.isPrimitive() && process.getActivityList() != null) {
                icon = TreeDto.ACTIVITY_ICON;
                for (Activity activity : process.getActivityList()) {
                    p = new TreeDto(activity.getId(), process.getId() + "", activity.getName(), icon);
                    data.add(p);
                }
            }
        }
        return data;
    }

    @GetMapping(path = "/process/{id}")
    public Process showProcess(@PathVariable long id) throws Exception {
        Process process = processService.findOne(id);
        if (process == null) {
            throw new Exception("There is no process with id " + id);
        }
        return process;
    }

    @PostMapping(path = "/process")
    public Process addProcess(@RequestBody ProcessCmd processCmd) {
        System.out.println("addProcess " + processCmd);
        return processService.save(processMapper.mapToEntity(processCmd));
    }

    @PutMapping(path = "/process/{id}")
    public Process editProcess(@PathVariable Long id, @RequestBody ProcessCmd processCmd) throws Exception {
        Process process = processService.findOne(id);
        if (process == null) {
            throw new Exception("There is no process with id " + id);
        }
        processMapper.updateEntityFromModel(processCmd, process);
        processService.update(processCmd, process);
        return process;
    }

    @GetMapping(path = "/activity/{id}")
    public Activity getActivity(@PathVariable long id) throws Exception {
        Activity activity = activityService.findOne(id);
        System.out.println(activity);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }
        return activity;
    }

    @PostMapping(path = "/activity")
    public Process addActivity(@RequestBody ActivityCmd activityCmd) throws Exception {
        System.out.println("addActivity " + activityCmd);
        Process process = processService.findOne(activityCmd.getProcessId());
        Activity activity = activityMapper.mapToEntity(activityCmd);
        process.getActivityList().add(activity);
        process = processService.save(process);
        return process;
    }

    @PutMapping(path = "/activity/{id}")
    public Activity editActivity(@PathVariable Long id, @RequestBody ActivityCmd activityCmd) throws Exception {
        Activity activity = activityService.findOne(id);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }
        activityMapper.updateEntityFromModel(activityCmd, activity);
        activityService.save(activity);
        return activity;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
