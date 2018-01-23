package company.dto;

import company.domain.Role;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private String username;
    private String name;
    private String surname;
    private Long companyId;
    private String companyName;
    private List<Role> roles = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" + "username='" + username + '\'' + ", name='" + name + '\'' + ", surname='" + surname + '\''
                + ", companyId=" + companyId + ", companyName='" + companyName + '\'' + ", roles=" + roles + '}';
    }
}
