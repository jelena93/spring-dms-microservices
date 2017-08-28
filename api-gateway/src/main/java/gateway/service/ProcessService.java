/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

import gateway.dto.Activity;
import gateway.dto.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author jelena
 */
@Service
public class ProcessService {

    private final String PROCESS_SERVICE = "http://process-service/";
    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    public Process findOne(Long id) {
        System.out.println("findOneProcess: " + id);
        Process process = oAuth2RestTemplate.getForObject(PROCESS_SERVICE + "/process/" + id, Process.class);
        return process;
    }

    public Activity findOneActivity(Long id) {
        System.out.println("findOneActivity: " + id);
        Activity activity = oAuth2RestTemplate.getForObject(PROCESS_SERVICE + "/activity/" + id, Activity.class);
        return activity;
    }

    public Process save(Process process) {
        System.out.println("saveProcess: " + process);
        process = oAuth2RestTemplate.postForObject(PROCESS_SERVICE + "/add", process, Process.class);
        System.out.println("response: " + process);
        return process;
    }

}
