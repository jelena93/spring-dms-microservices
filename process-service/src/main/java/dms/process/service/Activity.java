/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.process.service;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "activity")
public class Activity implements Serializable {

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
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinTable(name = "activity_input", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document"))
//    private List<Document> inputList;
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinTable(name = "activity_outputs", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document"))
//    private List<Document> outputList;

//    public Activity() {
//        this.inputList = new ArrayList<>();
//        this.outputList = new ArrayList<>();
//    }
    public Activity(String name) {
        this.name = name;
//        this.inputList = new ArrayList<>();
//        this.outputList = new ArrayList<>();
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

//    public List<Document> getInputList() {
//        return inputList;
//    }
//
//    public void setInputList(List<Document> inputList) {
//        this.inputList = inputList;
//    }
//
//    public List<Document> getOutputList() {
//        return outputList;
//    }
//
//    public void setOutputList(List<Document> outputList) {
//        this.outputList = outputList;
//    }
    @Override
    public int hashCode() {
        int hash = 3;
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
        final Activity other = (Activity) obj;
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
