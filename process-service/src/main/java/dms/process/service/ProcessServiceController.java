/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.process.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/")
public class ProcessServiceController {

    @Autowired
    ProcessRepository processRepository;
    @Autowired
    ActivityRepository activityRepository;

    @RequestMapping(path = "/{user}", method = RequestMethod.GET)
    public ResponseEntity<List<TreeDto>> getProcesses(@PathVariable("user") String user) {
        List<Proces> processes = processRepository.findByUser(user);
        List<TreeDto> data = new ArrayList<>();
        for (Proces process : processes) {
            TreeDto p;
            String icon;
            icon = TreeDto.PROCESS_ICON;
            if (process.getParent() == null) {
                p = new TreeDto(process.getId(), "#", process.getName(), icon, process.isPrimitive());
            } else {
                p = new TreeDto(process.getId(), process.getParent().getId() + "", process.getName(), icon, process.isPrimitive());
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

    @RequestMapping(path = "/process/{id}", method = RequestMethod.GET)
    public ResponseEntity<Proces> showProcess(@PathVariable("id") long id) throws Exception {
        Proces process = processRepository.findOne(id);
        if (process == null) {
            throw new Exception("There is no process with id " + id);
        }
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    @RequestMapping(path = "/activity/{id}", method = RequestMethod.GET)
    public ResponseEntity<Activity> showActivity(@PathVariable("id") long id) throws Exception {
        Activity activity = activityRepository.findOne(id);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Proces addProcess(@RequestBody Proces process) {
        System.out.println("saving " + process);
        return processRepository.save(process);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public ResponseEntity<String> editProcess(Long id, String name, boolean primitive) {
        Proces process = processRepository.findOne(id);
        if (process == null) {
            return new ResponseEntity<>("Process is null", HttpStatus.NOT_FOUND);
        }
        process.setName(name);
        if (process.isPrimitive() != primitive && primitive) {
            deleteProcessFromCompany(process);
        }
        if (process.isPrimitive() != primitive && !primitive) {
            process.getActivityList().clear();
        }
        process.setPrimitive(primitive);
        processRepository.save(process);
        return new ResponseEntity<>("Process successfully edited", HttpStatus.OK);
    }

    private void deleteProcessFromCompany(Proces process) {
//        Company company = userService.findOne(user.getUsername()).getCompany();
//        List<Process> processes = company.getProcesses();
//        deleteChildren(process, processes, true);
//        companyService.save(company);
    }

    private void deleteChildren(Proces process, List<Proces> processes, boolean root) {
        List<Proces> children = getChildren(process, processes);
        for (Proces child : children) {
            deleteChildren(child, processes, false);
        }
        if (!root) {
            processes.remove(process);
        }
    }

    private List<Proces> getChildren(Proces p, List<Proces> lista) {
        List<Proces> children = new ArrayList<>();
        for (Proces process : lista) {
            if (p != null && p.equals(process.getParent())) {
                children.add(process);
            }
        }
        return children;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> handleError(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new MessageDto(MessageDto.MESSAGE_TYPE_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
