package gateway.controller;

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
    public ModelAndView addCompany() {
        ModelAndView mv = new ModelAndView("add_user");
        mv.addObject("roles", auth2RestTemplate.getForObject("http://company-service/roles", List.class));
        mv.addObject("companies", auth2RestTemplate.getForObject("http://company-service/all", List.class));
        return mv;
    }
    
}
