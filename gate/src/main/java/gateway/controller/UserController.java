package gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    //    @Autowired
    //    private UserService userService;
    //    @Autowired
    //    private CompanyService companyService;

    //    @RequestMapping(path = "/add", method = RequestMethod.GET)
    //    public ModelAndView getAddUser() {
    //        List<Company> companies = companyService.findAll();
    //        ModelAndView mv = new ModelAndView("add_user");
    //        mv.addObject("roles", getRoles());
    //        mv.addObject("companies", companies);
    //        return mv;
    //    }
    //
    //    @RequestMapping(path = "/add", method = RequestMethod.POST)
    //    public ModelAndView save(String username, String password, String name, String surname,
    //            @RequestParam(name = "company", required = false) Long company, String[] roles) throws Exception {
    //        List<Role> rolesArr = new ArrayList<>();
    //        for (String role : roles) {
    //            if (role.equals(Role.ADMIN.name())) {
    //                rolesArr.add(Role.ADMIN);
    //            }
    //            if (role.equals(Role.USER.name())) {
    //                rolesArr.add(Role.USER);
    //            }
    //            if (role.equals(Role.UPLOADER.name())) {
    //                rolesArr.add(Role.UPLOADER);
    //            }
    //        }
    //        Company c;
    //        if (rolesArr.size() == 1 && rolesArr.contains(Role.ADMIN)) {
    //            c = null;
    //        } else if (company == null) {
    //            throw new Exception("Can't find company with id " + company);
    //        } else {
    //            c = companyService.findOne(company);
    //        }
    //        userService.save(username, password, name, surname, company, rolesArr);
    //        List<Company> companies = companyService.findAll();
    //        ModelAndView mv = new ModelAndView("add_user");
    //        mv.addObject("roles", getRoles());
    //        mv.addObject("companies", companies);
    //        mv.addObject("success_message", "User successfully added");
    //        return mv;
    //    }
    //
    //    private List<Role> getRoles() {
    //        List<Role> roles = new ArrayList<>();
    //        roles.add(Role.ADMIN);
    //        roles.add(Role.USER);
    //        roles.add(Role.UPLOADER);
    //        return roles;
    //    }

}
