/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.service;

import gateway.dto.Company;
import gateway.dto.Role;
import gateway.dto.User;
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
public class UserService {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    public User findOne(String username) {
        User user = oAuth2RestTemplate.getForObject("http://user-service/" + username, User.class);
        return user;
    }

    public List<User> findByCompanyId(Long companyId) {
        User[] users = oAuth2RestTemplate.getForObject("http://user-service/company/" + companyId, User[].class);
        return Arrays.asList(users);
    }

    public User save(String username, String password, String name, String surname, Long company, List<Role> roles) {
        System.out.println("@@@company " + company);
        User user = oAuth2RestTemplate.postForObject("http://user-service/add",
                new User(username, password, name, surname, company, roles), User.class);
        return user;
    }
}
