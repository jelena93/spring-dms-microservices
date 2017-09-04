/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.process.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
public class ProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public OAuth2RestTemplate auth2RestTemplate(OAuth2ProtectedResourceDetails resourceDetails, OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(resourceDetails, clientContext);
    }

}
