package company.command;

import company.domain.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserCmd {

    @NotEmpty(message = "{validation.user.username.empty}")
    @NotNull(message = "{validation.user.username.null}")
    private String username;

    @NotEmpty(message = "{validation.user.password.empty}")
    @NotNull(message = "{validation.user.password.null}")
    private String password;

    @NotEmpty(message = "{validation.user.name.empty}")
    @NotNull(message = "{validation.user.name.null}")
    private String name;

    @NotEmpty(message = "{validation.user.surname.empty}")
    @NotNull(message = "{validation.user.surname.null}")
    private String surname;

    private Long companyId;

    @NotEmpty(message = "{validation.user.role.empty}")
    private List<Role> roles;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserCmd{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\''
                + ", surname='" + surname + '\'' + ", companyId=" + companyId + ", roles=" + roles + '}';
    }
}
