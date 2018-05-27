package gateway.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gateway.dto.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final OAuth2RestTemplate auth2RestTemplate;

    @Autowired
    public CompanyController(OAuth2RestTemplate auth2RestTemplate) {
        this.auth2RestTemplate = auth2RestTemplate;
    }

    @GetMapping(path = "/add")
    public String addCompany() {
        return "add_company";
    }

    @GetMapping(path = "/search")
    public String searchCompanies() {
        return "search_companies";
    }

    @GetMapping(path = "/{companyId}")
//    @HystrixCommand(fallbackMethod = "showCompanyFallback")
    public ModelAndView showCompany(@PathVariable long companyId) {
        ModelAndView mv = new ModelAndView("company");
        CompanyDto companyDto = auth2RestTemplate.getForObject("http://company-service/" + companyId, CompanyDto.class);
        if (companyDto == null) {
            return new ModelAndView("redirect:/company/search");
        }
        mv.addObject("company", companyDto);
        return mv;
    }

    public ModelAndView showCompanyFallback(@PathVariable long companyId) {
        ModelAndView mv = new ModelAndView("redirect:/company/search");
        return mv;
    }
}
