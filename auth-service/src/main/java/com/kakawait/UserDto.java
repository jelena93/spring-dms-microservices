/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kakawait;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Jelena
 */
public class UserDto implements UserDetails {

    private final String username;
    private final String password;
    private final String name;
    private final String surname;
    private final List<Role> roles;
    private Role activeRole;

    public UserDto(String username, String password, String name, String surname, List<Role> roles, Role activeRole) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
        this.activeRole = activeRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setActiveRole(Role activeRole) {
        this.activeRole = activeRole;
    }

    public Role getActiveRole() {
        return activeRole;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String toString() {
        return "SecurityUser{" + "username=" + username + ", password=" + password + ", name=" + name + ", surname=" + surname + ", roles=" + roles + ", activeRole=" + activeRole + '}';
    }

}
