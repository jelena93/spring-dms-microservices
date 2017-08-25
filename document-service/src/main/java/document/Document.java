/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.management.Descriptor;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "document")
public class Document implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "document_id")
    @SequenceGenerator(name = "DocumentGen", sequenceName = "DOCUMENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "DocumentGen", strategy = GenerationType.SEQUENCE)
    @NotNull
    private Long id;
    @Column(name = "file_type")
    @NotNull
    private String fileType;
    @Column(name = "file_name")
    @NotNull
    private String fileName;
    @Column(name = "file_content", length = 1024 * 1024 * 25)
    @NotNull
    private byte[] fileContent;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "document_descriptors", joinColumns = @JoinColumn(name = "document"), inverseJoinColumns = @JoinColumn(name = "descriptor"))
    private List<Descriptor> descriptors;

    public Document() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public List<Descriptor> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<Descriptor> descriptors) {
        this.descriptors = descriptors;
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
        final Document other = (Document) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", fileType=" + fileType + ", fileName=" + fileName + ", descriptors=" + descriptors + '}';
    }

}
