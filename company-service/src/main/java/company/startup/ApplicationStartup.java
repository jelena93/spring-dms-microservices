package company.startup;

import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.service.CompanyService;
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

    @Autowired
    public ApplicationStartup(@Value("${addToDb}") boolean addToDb, CompanyService companyService) {
        this.addToDb = addToDb;
        this.companyService = companyService;
    }

    @Override
    public void afterPropertiesSet() {
        if (addToDb) {
            Company company = new Company("Silab d.o.o", "011111111", "01111111", "Vozdovac, Beograd");
            company = companyService.save(company);

            User admin = new User("Petar", "Petrovic", "admin", "admin", company,
                    new ArrayList<>(Arrays.asList(Role.ADMIN, Role.USER, Role.UPLOADER)));

            User user = new User("Marko", "Markovic", "user", "user", company,
                    new ArrayList<>(Arrays.asList(Role.USER, Role.UPLOADER)));
            company.getUserList().add(admin);
            company.getUserList().add(user);
            companyService.save(company);
        }
    }
}