/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kakawait;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jelena
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @Column(name = "company_id")
    private Long id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "pib")
    @NotNull
    private String pib;
    @Column(name = "identification_number")
    @NotNull
    private String identificationNumber;
    @Column(name = "headquarters")
    @NotNull
    private String headquarters;

    public Company(String name, String pib, String identificationNumber, String headquarters) {
        this.name = name;
        this.pib = pib;
        this.identificationNumber = identificationNumber;
        this.headquarters = headquarters;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Company{" + "id=" + id + ", name=" + name + ", pib=" + pib + ", identificationNumber=" + identificationNumber + ", headquarters=" + headquarters + '}';
    }

}
