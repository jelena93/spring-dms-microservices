package company.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
    private Long id;
    private String name;
    private String pib;
    private String identificationNumber;
    private String headquarters;
    private List<UserDto> userList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public List<UserDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDto> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pib='" + pib + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", headquarters='" + headquarters + '\'' +
                ", userList=" + userList +
                '}';
    }
}
