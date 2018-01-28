package process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import process.command.ActivityCmd;
import process.command.ProcessCmd;
import process.domain.Activity;
import process.domain.Process;
import process.dto.TreeDto;
import process.mapper.ActivityMapper;
import process.mapper.ProcessMapper;
import process.messaging.output.DocumentOutputMessagingService;
import process.messaging.output.dto.DocumentMessagingOutputDto;
import process.service.ActivityService;
import process.service.ProcessService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ProcessServiceController {

    private final ProcessService processService;
    private final ActivityService activityService;
    private final DocumentOutputMessagingService documentOutputMessagingService;
    private final ProcessMapper processMapper;
    private final ActivityMapper activityMapper;

    @Autowired
    public ProcessServiceController(ProcessService processService, ActivityService activityService,
                                    DocumentOutputMessagingService documentOutputMessagingService,
                                    ProcessMapper processMapper, ActivityMapper activityMapper) {
        this.processService = processService;
        this.activityService = activityService;
        this.documentOutputMessagingService = documentOutputMessagingService;
        this.processMapper = processMapper;
        this.activityMapper = activityMapper;
    }

    @GetMapping(path = "/all/{ownerId}")
    public List<TreeDto> getProcesses(@PathVariable long ownerId, OAuth2Authentication oAuth2Authentication) throws Exception {
        checkUser(ownerId, oAuth2Authentication);
        List<Process> processes = processService.findByOwnerId(ownerId);
        List<TreeDto> data = new ArrayList<>();
        for (Process process : processes) {
            TreeDto p;
            String icon;
            icon = TreeDto.PROCESS_ICON;
            if (process.isPrimitive()) {
                icon = TreeDto.PROCESS_PRIMITIVE_ICON;
            }
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
    public Process showProcess(@PathVariable long id, OAuth2Authentication oAuth2Authentication) throws Exception {
        Process process = processService.findOne(id);
        checkUser(process.getOwnerId(), oAuth2Authentication);
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
    public Process editProcess(@PathVariable Long id, @RequestBody ProcessCmd processCmd, OAuth2Authentication oAuth2Authentication) throws Exception {
        Process process = processService.findOne(id);
        checkUser(process.getOwnerId(), oAuth2Authentication);
        if (process == null) {
            throw new Exception("There is no process with id " + id);
        }
        List<Long> documentIds = new ArrayList<>();
        if (processCmd.isPrimitive()) {
            deleteChildren(process, processService.findByParent(process), documentIds, true);
        } else {
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getInputList().stream())
                    .collect(Collectors.toList()));
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getOutputList().stream())
                    .collect(Collectors.toList()));
        }
        System.out.println("a sadaaa " + documentIds);
        processMapper.updateEntityFromModel(processCmd, process);
        processService.update(processCmd, process);
        if (!documentIds.isEmpty()) {
            documentOutputMessagingService.sendDeleteDocuments(new DocumentMessagingOutputDto(documentIds));
        }
        return process;
    }


    @GetMapping(path = "/activity/{id}")
    public Activity getActivity(@PathVariable long id, OAuth2Authentication oAuth2Authentication) throws Exception {
        Activity activity = activityService.findOne(id);
        checkUser(activity.getProcess().getOwnerId(), oAuth2Authentication);
        System.out.println(activity);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }
        return activity;
    }

    @PostMapping(path = "/activity")
    public Process addActivity(@RequestBody ActivityCmd activityCmd, OAuth2Authentication oAuth2Authentication) throws Exception {
        System.out.println("addActivity " + activityCmd);
        Process process = processService.findOne(activityCmd.getProcessId());
        checkUser(process.getOwnerId(), oAuth2Authentication);
        Activity activity = activityMapper.mapToEntity(activityCmd);
        process.getActivityList().add(activity);
        process = processService.save(process);
        return process;
    }

    @PutMapping(path = "/activity/{id}")
    public Activity editActivity(@PathVariable Long id, @RequestBody ActivityCmd activityCmd, OAuth2Authentication oAuth2Authentication) throws Exception {
        Activity activity = activityService.findOne(id);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }
        checkUser(activity.getProcess().getOwnerId(), oAuth2Authentication);
        activityMapper.updateEntityFromModel(activityCmd, activity);
        activityService.save(activity);
        return activity;
    }

    private static void checkUser(Long ownerId, OAuth2Authentication oAuth2Authentication) throws Exception {
        Map<String, Object> details = (Map<String, Object>) oAuth2Authentication.getUserAuthentication().getDetails();
        Map<String, Object> principal = (Map<String, Object>) details.get("principal");
        System.out.println(principal.get("companyId"));
        if (ownerId != Long.valueOf(principal.get("companyId").toString())) {
            throw new Exception("Not allowed");
        }
    }

    private static void deleteChildren(Process process, List<Process> processes, List<Long> documentIds, boolean root) {
        List<Process> children = getChildren(process, processes);
        for (Process child : children) {
            deleteChildren(child, processes, documentIds, false);
        }
        if (!root) {
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getInputList().stream())
                    .collect(Collectors.toList()));
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getOutputList().stream())
                    .collect(Collectors.toList()));
        }
    }

    private static List<Process> getChildren(Process p, List<Process> lista) {
        List<Process> children = new ArrayList<>();
        for (Process process : lista) {
            if (p != null && p.equals(process.getParent())) {
                children.add(process);
            }
        }
        return children;
    }

}
