package gateway.controller;

import gateway.dto.DocumentTypeDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/process")
public class ProcessController {

    private final OAuth2RestTemplate auth2RestTemplate;

    @Autowired
    public ProcessController(OAuth2RestTemplate auth2RestTemplate) {
        this.auth2RestTemplate = auth2RestTemplate;
    }

    @GetMapping(path = "/add")
    public ModelAndView addProcess() {
        ModelAndView mv = new ModelAndView("add_process");
        List<DocumentTypeDto> list = auth2RestTemplate.getForObject("http://descriptor-service/document-type/all", List.class);
        mv.addObject("documentTypes", list);
        return mv;
    }

}
