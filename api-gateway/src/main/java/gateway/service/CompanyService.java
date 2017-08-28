/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

import gateway.dto.Company;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author jelena
 */
@Service
public class CompanyService {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    public Company save(String name, String pib, String identificationNumber, String headquarters) {
        Company company = oAuth2RestTemplate.postForObject("http://company-service/add",
                new Company(name, pib, identificationNumber, headquarters), Company.class);
        return company;
    }

    public Company edit(Long id, String name, String pib, String identificationNumber, String headquarters) {
        Company company = oAuth2RestTemplate.postForObject("http://company-service/edit",
                new Company(id, name, pib, identificationNumber, headquarters), Company.class);
        return company;
    }

    public List<Company> findAll() {
        Company[] companies = oAuth2RestTemplate.getForObject("http://company-service/all", Company[].class);
        return Arrays.asList(companies);
    }

    public Company findOne(Long id) {
        Company company = oAuth2RestTemplate.getForObject("http://company-service/" + id, Company.class);
        return company;
    }
}
