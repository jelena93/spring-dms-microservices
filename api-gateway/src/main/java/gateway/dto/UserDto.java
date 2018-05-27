package gateway.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private String username;
    private String name;
    private String surname;
    private List<String> roles = new ArrayList<>();

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{"
                + "username='" + username + '\''
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", roles=" + roles
                + '}';
    }
}
