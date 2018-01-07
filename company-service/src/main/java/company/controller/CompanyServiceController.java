package company.controller;

import company.command.UserCmd;
import company.domain.Company;
import company.domain.User;
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
    private final UserMapper userMapper;

    @Autowired
    public CompanyServiceController(CompanyService companyService, UserService userService, UserMapper userMapper) {
        this.companyService = companyService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "/all")
    public List<Company> getCompanies() {
        return companyService.findAll();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Company>> search(String query) {
        if (query == null || query.isEmpty()) {
            return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(companyService.search(query), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public Company getCompanyById(@PathVariable long id) throws Exception {
        Company company = companyService.findOne(id);
        if (company == null) {
            throw new Exception("There is no company with id " + id);
        }
        return company;
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        System.out.println("addCompany " + company);
        company = companyService.save(company);
        return company;
    }

    @PutMapping
    public Company editCompany(@RequestBody Company company) {
        System.out.println("editCompany " + company);
        return companyService.save(company);
    }

    @PostMapping("/user")
    public User addUser(@RequestBody UserCmd userCmd) throws Exception {
        System.out.println("addUserToCompany " + userCmd);
        return userService.save(userMapper.mapToEntity(userCmd));
    }

}
