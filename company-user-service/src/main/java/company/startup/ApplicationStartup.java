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
    public ApplicationStartup(@Value("${add-to-db}") boolean addToDb, CompanyService companyService,
                              UserService userService) {
        this.addToDb = addToDb;
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        if (addToDb) {
            User user = new User("admin", "admin", "admin", "admin", null, new ArrayList<>(Arrays.asList(Role.ADMIN)));
            userService.save(user);

            Company company = new Company("Silab d.o.o", "011111111", "01111111", "Vozdovac, Beograd");
            company = companyService.save(company);
            user = new User("asd", "asd", "asd", "asd", company,
                            new ArrayList<>(Arrays.asList(Role.USER, Role.UPLOADER)));
            user.setCompany(company);
            userService.save(user);

        }
    }
}