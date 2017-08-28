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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jelena
 */
@Entity
@Table(name = "document_type")
public class DocumentType implements Serializable {

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "DocumentTypeGen", sequenceName = "DOCUMENT_TYPE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "DocumentTypeGen", strategy = GenerationType.SEQUENCE)
    @Column(name = "document_type_id")
    @NotNull
    private Long id;
    @Column(name = "name")
    @NotNull
    private String name;
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "document_type")
//    private List<Descriptor> descriptors;

    public DocumentType() {
//        descriptors = new ArrayList<>();
    }

    public DocumentType(Long id, String name) {
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

//    public List<Descriptor> getDescriptors() {
//        return descriptors;
//    }
//
//    public void setDescriptors(List<Descriptor> descriptors) {
//        this.descriptors = descriptors;
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
        final DocumentType other = (DocumentType) obj;
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
