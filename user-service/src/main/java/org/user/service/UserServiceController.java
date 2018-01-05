package org.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserServiceController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable("id") String username) throws Exception {
        return userRepository.findOne(username);
    }

    @RequestMapping(path = "/company/{id}", method = RequestMethod.GET)
    public List<User> findByCompanyId(@PathVariable("id") Long id) {
        return userRepository.findByCompanyId(id);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public User save(@RequestBody User user) {
        System.out.println("User: " + user);
        return userRepository.save(user);
    }
}
