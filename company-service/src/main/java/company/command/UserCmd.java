package company.command;

import company.domain.Role;

import java.util.List;

public class UserCmd {

    private String username;
    private String password;
    private String name;
    private String surname;
    private Long companyId;
    private List<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<Role> getRoles() {
        return roles;
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
