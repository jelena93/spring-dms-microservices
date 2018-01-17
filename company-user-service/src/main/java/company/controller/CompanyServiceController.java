package company.controller;

import company.command.UserCmd;
import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.dto.CompanyDto;
import company.dto.UserDto;
import company.mapper.CompanyMapper;
import company.mapper.UserMapper;
import company.service.CompanyService;
import company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    @Autowired
    public CompanyServiceController(CompanyService companyService, UserService userService, CompanyMapper companyMapper,
                                    UserMapper userMapper) {
        this.companyService = companyService;
        this.userService = userService;
        this.companyMapper = companyMapper;
        this.userMapper = userMapper;
    }

    @Autowired

    @GetMapping("/all")
    public List<CompanyDto> getCompanies() {
        return companyMapper.mapToModelList(companyService.findAll());
    }

    @GetMapping("/search")
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
    public CompanyDto getCompanyById(@PathVariable long id) throws Exception {
        Company company = companyService.findOne(id);
        if (company == null) {
            throw new Exception("There is no company with id " + id);
        }
        return companyMapper.mapToModel(company);
    }

    @PostMapping
    public CompanyDto addCompany(@RequestBody Company company) {
        System.out.println("addCompany " + company);
        return companyMapper.mapToModel(companyService.save(company));
    }

    @PutMapping
    public CompanyDto editCompany(@RequestBody Company company) {
        System.out.println("editCompany " + company);
        return companyMapper.mapToModel(companyService.save(company));
    }

    @PostMapping("/user")
    public UserDto addUser(@RequestBody UserCmd userCmd) throws Exception {
        System.out.println("addUserToCompany " + userCmd);
        return userMapper.mapToModel(userService.save(userMapper.mapToEntity(userCmd)));
    }

    @GetMapping("/roles")
    public Role[] getRoles() {
        return Role.values();
    }

}
