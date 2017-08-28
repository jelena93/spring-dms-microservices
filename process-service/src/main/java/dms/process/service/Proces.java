/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.process.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "process")
public class Proces implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @TableGenerator(table = "seq_gen", name = "seq_gen", pkColumnName = "seq_name", valueColumnName = "seq_val",
            pkColumnValue = "id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_gen")
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "user")
    private String user;
    @JoinColumn(name = "parent", nullable = true)
    @ManyToOne
    private Proces parent;
    @Column(name = "primitive")
    private boolean primitive;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "process_activity", joinColumns = @JoinColumn(name = "process"), inverseJoinColumns = @JoinColumn(name = "activity"))
    private List<Activity> activityList;

    public Proces() {
        this.activityList = new ArrayList<>();
    }

    public Proces(String name, Proces parent, boolean primitive, String user) {
        this.name = name;
        this.parent = parent;
        this.primitive = primitive;
        this.user = user;
        this.activityList = new ArrayList<>();
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

    public Proces getParent() {
        return parent;
    }

    public void setParent(Proces parent) {
        this.parent = parent;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Proces other = (Proces) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
