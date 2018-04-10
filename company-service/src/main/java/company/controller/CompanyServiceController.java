package company.controller;

import company.command.CompanyCmd;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @GetMapping("/{id}")
    public CompanyDto getCompanyById(@PathVariable long id, OAuth2Authentication oAuth2Authentication) throws Exception {
        Company company = companyService.findOne(id);
        checkUser(id, oAuth2Authentication);
        return companyMapper.mapToModel(company);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto addCompany(@RequestBody @Valid CompanyCmd companyCmd) {
        System.out.println("addCompany " + companyCmd);
        return companyMapper.mapToModel(companyService.save(companyMapper.mapToEntity(companyCmd)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto editCompany(@PathVariable long id, @RequestBody @Valid CompanyCmd companyCmd) throws Exception {
        Company company = companyService.findOne(id);
        if (company == null) throw new Exception("There is no company with id " + id);
        System.out.println("editCompany " + companyCmd);
        companyMapper.updateEntityFromModel(companyCmd, company);
        return companyMapper.mapToModel(companyService.save(company));
    }

    @GetMapping("/user/{username}")
    public UserDto getUser(@PathVariable String username, Principal principal) throws Exception {
        UserDto userDto = userMapper.mapToModel(userService.findOne(username));
        if (!principal.getName().equals(userDto.getUsername())) {
            throw new Exception("Not allowed to see other users");
        }
        System.out.println(userDto);
        return userDto;
    }

    @GetMapping("/user/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> searchUsers(String query) throws Exception {
        if (query == null || query.isEmpty()) return userMapper.mapToModelList(userService.findAll());
        return userMapper.mapToModelList(userService.search(query));
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto addUser(@RequestBody @Valid UserCmd userCmd) throws Exception {
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

    private static void checkUser(Long ownerId, OAuth2Authentication oAuth2Authentication) throws Exception {
        Map<String, Object> details = (Map<String, Object>) oAuth2Authentication.getUserAuthentication().getDetails();
        Map<String, Object> principal = (Map<String, Object>) details.get("principal");
        System.out.println(principal.get("companyId"));

        if (!oAuth2Authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !Objects.equals(ownerId, Long.valueOf(principal.get("companyId").toString()))) {
            throw new Exception("Not allowed");
        }
    }
}
