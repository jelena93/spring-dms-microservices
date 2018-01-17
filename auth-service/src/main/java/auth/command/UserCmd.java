package auth.command;

import java.util.List;

public class UserCmd {
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean activated = true;
    private List<String> roles;

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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserCmd{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\''
                + ", surname='" + surname + '\'' + ", activated=" + activated + ", roles=" + roles + '}';
    }
}
