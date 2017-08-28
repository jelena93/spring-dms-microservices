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
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "activity_input_document_types")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Long> inputListDocumentTypes = new ArrayList<Long>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @CollectionTable(name = "activity_output_document_types")
    private List<Long> outputListDocumentTypes = new ArrayList<Long>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "activity_inputs")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Long> inputList = new ArrayList<Long>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @CollectionTable(name = "activity_outputs")
    private List<Long> outputList = new ArrayList<Long>();
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinTable(name = "activity_input", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document"))
//    @Fetch(value = FetchMode.SUBSELECT)
//    private List<Document> inputList;
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @Fetch(value = FetchMode.SUBSELECT)
//    @JoinTable(name = "activity_outputs", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document"))
//    private List<Document> outputList;
////    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @Fetch(value = FetchMode.SUBSELECT)
//    @JoinTable(name = "activity_input_document_types", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document_type"))
//    private List<DocumentType> inputListDocumentTypes;
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @Fetch(value = FetchMode.SUBSELECT)
//    @JoinTable(name = "activity_output_document_types", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document_type"))
//    private List<DocumentType> outputListDocumentTypes;

    public Activity() {
    }

    public Activity(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Long> getInputListDocumentTypes() {
        return inputListDocumentTypes;
    }

    public void setInputListDocumentTypes(List<Long> inputListDocumentTypes) {
        this.inputListDocumentTypes = inputListDocumentTypes;
    }

    public List<Long> getOutputListDocumentTypes() {
        return outputListDocumentTypes;
    }

    public void setOutputListDocumentTypes(List<Long> outputListDocumentTypes) {
        this.outputListDocumentTypes = outputListDocumentTypes;
    }

    public List<Long> getInputList() {
        return inputList;
    }

    public void setInputList(List<Long> inputList) {
        this.inputList = inputList;
    }

    public List<Long> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<Long> outputList) {
        this.outputList = outputList;
    }

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
        return "Activity{" + "id=" + id + ", name=" + name + ", inputListDocumentTypes=" + inputListDocumentTypes + ", outputListDocumentTypes=" + outputListDocumentTypes + ", inputList=" + inputList + ", outputList=" + outputList + '}';
    }

}
