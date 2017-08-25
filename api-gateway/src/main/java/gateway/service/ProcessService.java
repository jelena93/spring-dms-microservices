/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

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

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    public Process findOne(Long id) {
        System.out.println("findOneProcess: " + id);
        Process process = oAuth2RestTemplate.getForObject("http://process-service/process/" + id, Process.class);
        return process;
    }

    public Process save(Process process) {
        System.out.println("saveProcess: " + process);
        process = oAuth2RestTemplate.postForObject("http://process-service/add", process, Process.class);
        return process;
    }
}
