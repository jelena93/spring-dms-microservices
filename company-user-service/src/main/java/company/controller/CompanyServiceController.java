package company.controller;

import company.command.UserCmd;
import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.dto.CompanyDto;
import company.dto.UserDto;
import company.mapper.CompanyMapper;
import company.mapper.UserMapper;
import company.messaging.UserMessagingService;
import company.service.CompanyService;
import company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class CompanyServiceController {

    private final CompanyService companyService;
    private final UserService userService;
    private final UserMessagingService userMessagingService;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    @Autowired
    public CompanyServiceController(CompanyService companyService, UserService userService,
                                    UserMessagingService userMessagingService, CompanyMapper companyMapper,
                                    UserMapper userMapper) {
        this.companyService = companyService;
        this.userService = userService;
        this.userMessagingService = userMessagingService;
        this.companyMapper = companyMapper;
        this.userMapper = userMapper;
    }

    @Autowired

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CompanyDto> getCompanies() {
        return companyMapper.mapToModelList(companyService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CompanyDto> search(String query) {
        if (query == null || query.isEmpty()) {
            return companyMapper.mapToModelList(companyService.findAll());
        }
        return companyMapper.mapToModelList(companyService.search(query));
    }

    //    @GetMapping({ "/swagger", "/docs" })
    //    public String redirectSwagger() {
    //        return "redirect:/swagger-ui.html";
    //    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto getCompanyById(@PathVariable long id) throws Exception {
        Company company = companyService.findOne(id);
        if (company == null) {
            throw new Exception("There is no company with id " + id);
        }
        return companyMapper.mapToModel(company);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto addCompany(@RequestBody Company company) {
        System.out.println("addCompany " + company);
        return companyMapper.mapToModel(companyService.save(company));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto editCompany(@RequestBody Company company) {
        System.out.println("editCompany " + company);
        return companyMapper.mapToModel(companyService.save(company));
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_UPLOADER')")
    public UserDto getUser(@PathVariable String username) {
        UserDto userDto = userMapper.mapToModel(userService.findOne(username));
        System.out.println(userDto);
        return userDto;
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto addUser(@RequestBody UserCmd userCmd) throws Exception {
        System.out.println("addUserToCompany " + userCmd);
        User user = userService.save(userMapper.mapToEntity(userCmd));
        userMessagingService.sendUserAdded(user);
        return userMapper.mapToModel(user);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Role[] getRoles() {
        return Role.values();
    }

}
