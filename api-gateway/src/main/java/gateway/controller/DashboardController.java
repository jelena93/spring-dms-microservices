package gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class DashboardController {

    @GetMapping
    public ModelAndView dashboard(Authentication authentication) {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        System.out.println("authentication " + authentication);
        model.addObject("user", authentication);
        return model;
    }
}
