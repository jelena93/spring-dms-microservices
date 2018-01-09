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
    public String searchCompany() {
        return "search_companies";
    }

    @GetMapping(path = "/{companyId}")
    public String showCompany(@PathVariable long companyId) {
        return "company";
    }

    //    @RequestMapping(path = "/add", method = RequestMethod.POST)
    //    public ModelAndView save(String name, String pib, String identificationNumber, String headquarters) throws
    // Exception {
    //        companyService.save(name, pib, identificationNumber, headquarters);
    //        return new ModelAndView("add_company", "message", new MessageDto(MessageDto.MESSAGE_TYPE_SUCCESS,
    // "Company successfully added"));
    //    }
    //
    //    @RequestMapping(path = "/search", method = RequestMethod.GET)
    //    public ModelAndView findAll() {
    //        ModelAndView mv = new ModelAndView("search_companies");
    //        mv.addObject("companies", companyService.findAll());
    //        return mv;
    //    }
    //
    //
    //    @RequestMapping(path = "/company/{id}", method = RequestMethod.POST)
    //    public String editCompany(@PathVariable("id") long id, String name, String pib, String
    // identificationNumber, String headquarters) {
    //        companyService.edit(id, name, pib, identificationNumber, headquarters);
    ////        List<User> usersOfCompany = userService.findByCompanyId(id);
    ////        ModelAndView mv = new ModelAndView("company");
    ////        mv.addObject("company", company);
    ////        mv.addObject("users", usersOfCompany);
    //        return "redirect:/companies/company/" + id;
    //    }
}
