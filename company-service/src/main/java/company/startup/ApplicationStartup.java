package company.startup;

import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.service.CompanyService;
import company.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ApplicationStartup implements InitializingBean {
    private final boolean addToDb;
    private final CompanyService companyService;
    private final UserService userService;

    @Autowired
    public ApplicationStartup(@Value("${addToDb}") boolean addToDb, CompanyService companyService, UserService userService) {
        this.addToDb = addToDb;
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        if (addToDb) {
            Company company = new Company("company d.o.o", "011111111", "01111111", "Vracar");
            company = companyService.save(company);

            User admin = new User("Petar", "Petrovic", "pera", company,
                    new ArrayList<>(Arrays.asList(Role.ADMIN)));
            userService.save(admin);

            User user = new User("Marko", "Markovic", "marko", company,
                    new ArrayList<>(Arrays.asList(Role.USER, Role.UPLOADER)));
            company.getUserList().add(user);
            companyService.save(company);
        }
    }
}