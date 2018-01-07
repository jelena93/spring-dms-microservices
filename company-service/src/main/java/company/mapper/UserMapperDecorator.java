package company.mapper;

import company.command.UserCmd;
import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserMapperDecorator implements UserMapper {
    @Autowired
    private UserMapper delegate;
    @Autowired
    private CompanyService companyService;

    @Override
    public User mapToEntity(UserCmd userCmd) throws Exception {
        User user = delegate.mapToEntity(userCmd);
        Company company;
        System.out.println(userCmd.getRoles());
        if (userCmd.getRoles().isEmpty()) {
            throw new Exception("At least one role is required");
        }
        if (userCmd.getRoles().size() == 1 && userCmd.getRoles().contains(Role.ADMIN)) {
            company = null;
            System.out.println("ovde");
        } else {
            company = companyService.findOne(userCmd.getCompanyId());
            if (company == null) {
                throw new Exception("There is no company with id: " + userCmd.getCompanyId());
            }
        }
        System.out.println(company);
        user.setCompany(company);
        System.out.println(user.getCompany());
        return user;
    }

}