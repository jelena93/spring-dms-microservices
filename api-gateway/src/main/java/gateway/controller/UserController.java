package gateway.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final OAuth2RestTemplate auth2RestTemplate;

    @Autowired
    public UserController(OAuth2RestTemplate auth2RestTemplate) {
        this.auth2RestTemplate = auth2RestTemplate;
    }

    @GetMapping(path = "/add")
//    @HystrixCommand(fallbackMethod = "addUserFallback")
    public ModelAndView addUser() {
        ModelAndView mv = new ModelAndView("add_user");
        mv.addObject("roles", auth2RestTemplate.getForObject("http://company-service/roles", List.class));
        mv.addObject("companies", auth2RestTemplate.getForObject("http://company-service/all", List.class));
        return mv;
    }

    @GetMapping(path = "/search")
    public String searchUsers() {
        return "search_users";
    }

    public ModelAndView addUserFallback() {
        ModelAndView mv = new ModelAndView("add_user");
        mv.addObject("roles", Arrays.asList(new String[]{"ADMIN", "USER", "UPLOADER"}));
        mv.addObject("companies", new ArrayList());
        return mv;
    }
}
