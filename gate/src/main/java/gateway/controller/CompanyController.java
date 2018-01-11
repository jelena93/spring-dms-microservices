package gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @GetMapping(path = "/add")
    public String addCompany() {
        return "add_company";
    }

    @GetMapping(path = "/search")
    public String searchCompanies() {
        return "search_companies";
    }

    @GetMapping(path = "/{companyId}")
    public String showCompany(@PathVariable long companyId) {
        return "company";
    }

}
