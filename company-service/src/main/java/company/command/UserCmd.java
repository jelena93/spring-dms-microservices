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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserCmd{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\''
                + ", surname='" + surname + '\'' + ", companyId=" + companyId + ", roles=" + roles + '}';
    }
}
