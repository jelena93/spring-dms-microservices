package process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import process.command.ActivityCmd;
import process.command.ProcessCmd;
import process.domain.Activity;
import process.domain.Process;
import process.dto.ActivityDto;
import process.dto.ProcessDto;
import process.dto.TreeDto;
import process.mapper.ActivityMapper;
import process.mapper.ProcessMapper;
import process.messaging.output.DocumentMessagingService;
import process.messaging.output.dto.DocumentMessagingOutputDto;
import process.service.ActivityService;
import process.service.ProcessService;
import process.validator.ProcessValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ProcessServiceController {

    private final ProcessService processService;
    private final ActivityService activityService;
    private final DocumentMessagingService documentMessagingService;
    private final ProcessMapper processMapper;
    private final ActivityMapper activityMapper;
    private final ProcessValidator processValidator;
    private final ActivityValidator activityValidator;

    @Autowired
    public ProcessServiceController(ProcessService processService, ActivityService activityService,
                                    DocumentMessagingService documentMessagingService,
                                    ProcessMapper processMapper, ActivityMapper activityMapper, ProcessValidator processValidator, ActivityValidator activityValidator) {
        this.processService = processService;
        this.activityService = activityService;
        this.documentMessagingService = documentMessagingService;
        this.processMapper = processMapper;
        this.activityMapper = activityMapper;
        this.processValidator = processValidator;
        this.activityValidator = activityValidator;
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
    public ProcessDto showProcess(@PathVariable long id, OAuth2Authentication oAuth2Authentication) throws Exception {
        Process process = processService.findOne(id);
        checkUser(process.getOwnerId(), oAuth2Authentication);
        return processMapper.mapToModel(process);
    }

    @PostMapping(path = "/process")
    public ProcessDto addProcess(@RequestBody @Valid ProcessCmd processCmd) throws Exception {
        System.out.println("addProcess " + processCmd);

        processValidator.validate(processCmd);

        return processMapper.mapToModel(processService.save(processMapper.mapToEntity(processCmd)));
    }

    @PutMapping(path = "/process/{id}")
    public ProcessDto editProcess(@PathVariable Long id, @RequestBody @Valid ProcessCmd processCmd, OAuth2Authentication oAuth2Authentication) throws Exception {
        Process process = processService.findOne(id);
        if (process == null) throw new Exception("There is no process with id " + id);
        checkUser(process.getOwnerId(), oAuth2Authentication);

        processValidator.validate(processCmd);

        List<Long> documentIds = new ArrayList<>();
        if (processCmd.isPrimitive() && !process.isPrimitive()) {
            deleteChildren(process, processService.findByParent(process), documentIds, true);
        } else if (!processCmd.isPrimitive() && process.isPrimitive()) {
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getInputList().stream())
                    .collect(Collectors.toList()));
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getOutputList().stream())
                    .collect(Collectors.toList()));
        }
        processMapper.updateEntityFromModel(processCmd, process);
        process = processService.update(processCmd, process);
        if (!documentIds.isEmpty()) {
            documentMessagingService.sendDeleteDocuments(new DocumentMessagingOutputDto(documentIds));
        }
        return processMapper.mapToModel(process);
    }


    @GetMapping(path = "/activity/{id}")
    public ActivityDto getActivity(@PathVariable long id, OAuth2Authentication oAuth2Authentication) throws Exception {
        Activity activity = activityService.findOne(id);
        checkUser(activity.getProcess().getOwnerId(), oAuth2Authentication);
        System.out.println(activity);
        return activityMapper.mapToModel(activity);
    }

    @PostMapping(path = "/activity")
    public ActivityDto addActivity(@RequestBody @Valid ActivityCmd activityCmd, OAuth2Authentication oAuth2Authentication) throws Exception {
        System.out.println("addActivity " + activityCmd);
        Process process = processService.findOne(activityCmd.getProcessId());
        if (process == null) throw new Exception("Process is required");

        activityValidator.validate(activityCmd);

        checkUser(process.getOwnerId(), oAuth2Authentication);

        Activity activity = activityMapper.mapToEntity(activityCmd);
        process.getActivityList().add(activity);
        processService.save(process);
        return activityMapper.mapToModel(activity);
    }

    @PutMapping(path = "/activity/{id}")
    public ActivityDto editActivity(@PathVariable Long id, @RequestBody @Valid ActivityCmd activityCmd, OAuth2Authentication oAuth2Authentication) throws Exception {
        Activity activity = activityService.findOne(id);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }
        checkUser(activity.getProcess().getOwnerId(), oAuth2Authentication);
        activityMapper.updateEntityFromModel(activityCmd, activity);
        return activityMapper.mapToModel(activityService.save(activity));
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
