package company;

import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    private final boolean addToDb;
    private final CompanyService companyService;

    @Autowired
    public ApplicationStartup(@Value("${add-to-db}") boolean addToDb, CompanyService companyService) {
        this.addToDb = addToDb;
        this.companyService = companyService;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {
        if (addToDb) {
            Company company = new Company("Silab d.o.o", "011111111", "01111111", "Vozdovac, Beograd");
            company = companyService.save(company);

            User user = new User("DUULE", "SAVIC!", "asd", "asd", company,
                                 new ArrayList<>(Arrays.asList(Role.ADMIN, Role.USER, Role.UPLOADER)));
            user.setCompany(company);
            companyService.save(company);
        }
    }
}