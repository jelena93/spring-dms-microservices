package company.mapper;

import company.command.UserCmd;
import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserMapperDecorator implements UserMapper {
    @Autowired
    private UserMapper delegate;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User mapToEntity(UserCmd userCmd) throws Exception {
        User user = delegate.mapToEntity(userCmd);
        Company company;
        if (userCmd.getRoles().isEmpty()) {
            throw new Exception("At least one role is required");
        }
        if (userCmd.getRoles().size() == 1 && userCmd.getRoles().contains(Role.ADMIN)) {
            company = null;
        } else {
            if (userCmd.getCompanyId() == null) throw new Exception("Company is required");
            company = companyService.findOne(userCmd.getCompanyId());
            if (company == null) throw new Exception("There is no company with id: " + userCmd.getCompanyId());

        }
        user.setCompany(company);
        userCmd.setPassword(passwordEncoder.encode(userCmd.getPassword()));
        return user;
    }

}