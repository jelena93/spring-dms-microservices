/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.dto;

import java.util.List;

/**
 *
 * @author jelena
 */
public class User {

    private String username;
    private String password;
    private String name;
    private String surname;
    private Long company;
    private List<Role> roles;

    public User() {
    }

    public User(String username, String password, String name, String surname, Long company, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.company = company;
        this.roles = roles;
    }

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

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
