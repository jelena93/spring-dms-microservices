package process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import process.domain.Activity;
import process.domain.Process;
import process.dto.TreeDto;
import process.repository.ActivityRepository;
import process.repository.ProcessRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class ProcessServiceController {

    @Autowired
    ProcessRepository processRepository;
    @Autowired
    ActivityRepository activityRepository;

    @GetMapping(path = "/all/{user}")
    public ResponseEntity<List<TreeDto>> getProcesses(@PathVariable String user) {
        List<Process> processes = processRepository.findByUser(user);
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
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(path = "/process/{id}")
    public ResponseEntity<Process> showProcess(@PathVariable long id) throws Exception {
        Process process = processRepository.findOne(id);
        if (process == null) {
            throw new Exception("There is no process with id " + id);
        }
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    @PostMapping(path = "/process")
    public Process addProcess(@RequestBody Process process) {
        System.out.println("saving " + process);
        return processRepository.save(process);
    }

    @PutMapping(path = "/process/{id}")
    public ResponseEntity<String> editProcess(@PathVariable Long id, String name, boolean primitive) {
        Process process = processRepository.findOne(id);
        if (process == null) {
            return new ResponseEntity<>("Process is null", HttpStatus.NOT_FOUND);
        }
        process.setName(name);
        if (process.isPrimitive() != primitive && primitive) {
            deleteChildrenFromProcess(process);
        }
        if (process.isPrimitive() != primitive && !primitive) {
            process.getActivityList().clear();
        }
        process.setPrimitive(primitive);
        processRepository.save(process);
        return new ResponseEntity<>("Process successfully edited", HttpStatus.OK);
    }

    @PostMapping(path = "/activity")
    public Activity addActivity(@RequestBody Activity activity) throws Exception {
        System.out.println("addActivity " + activity);
        return activityRepository.save(activity);
    }

    @GetMapping(path = "/activity/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable long id) throws Exception {
        Activity activity = activityRepository.findOne(id);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @PutMapping(path = "/activity/{id}")
    public ResponseEntity<String> editActivity(@PathVariable Long id, String name, Long[] inputListDocumentTypes,
                                               Long[] outputListDocumentTypes) {
        Activity activity = activityRepository.findOne(id);
        activity.setName(name);
        activity.setInputListDocumentTypes(Arrays.asList(inputListDocumentTypes));
        activity.setOutputListDocumentTypes(Arrays.asList(outputListDocumentTypes));
        activityRepository.save(activity);
        return new ResponseEntity<>("Activity successfully edited", HttpStatus.OK);
    }

    private List<Process> getChildren(Process p, List<Process> lista) {
        List<Process> children = new ArrayList<>();
        for (Process process : lista) {
            if (p != null && p.equals(process.getParent())) {
                children.add(process);
            }
        }
        return children;
    }

    private void deleteChildrenFromProcess(Process process) {
        System.out.println("deleting child from " + process.getName());
        Process child = processRepository.findByParent(process);
        System.out.println("child: " + child.getName());
        processRepository.delete(child);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
