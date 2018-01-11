package gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @GetMapping(path = "/add")
    public String addProcess() {
        return "add_process";
    }

}
