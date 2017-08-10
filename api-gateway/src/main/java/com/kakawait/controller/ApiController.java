/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kakawait.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author jelena
 */
@Controller
@RequestMapping("/")
public class ApiController {

    @RequestMapping(method = RequestMethod.GET)
    public String losgin(Authentication principal) {
        System.out.println("asd: " + principal);
        if (principal == null) {
            return "redirect:login";
        }
//        restTemplate.getForEntity(String.format("http://company-service/all", Map.class));
//        String fooResourceUrl
//                = "http://company-service/all";
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(fooResourceUrl, String.class);
//        System.out.println("response " + response);
        return "admin_home";
    }
}
