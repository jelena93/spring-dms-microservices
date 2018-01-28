package process.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 2951083015809447338L;
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
    @CollectionTable(name = "activity_input_document_types", uniqueConstraints = @UniqueConstraint(columnNames = {
            "activity_id", "document_type_id"}))
    @Column(name = "document_type_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Long> inputListDocumentTypes = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @CollectionTable(name = "activity_output_document_types", uniqueConstraints = @UniqueConstraint(columnNames = {
            "activity_id", "document_type_id"}))
    @Column(name = "document_type_id")
    private List<Long> outputListDocumentTypes = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "activity_inputs", uniqueConstraints = @UniqueConstraint(columnNames = {"activity_id",
            "document_id"}))
    @Column(name = "document_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Long> inputList = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @CollectionTable(name = "activity_outputs", uniqueConstraints = @UniqueConstraint(columnNames = {"activity_id",
            "document_id"}))
    @Column(name = "document_id")
    private List<Long> outputList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;

    public Activity() {
    }

    public Activity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Activity(String name) {
        this.name = name;
        this.inputList = new ArrayList<>();
        this.outputList = new ArrayList<>();
        this.inputListDocumentTypes = new ArrayList<>();
        this.outputListDocumentTypes = new ArrayList<>();
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

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
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
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + id + ", name=" + name + ", inputListDocumentTypes=" + inputListDocumentTypes
                + ", outputListDocumentTypes=" + outputListDocumentTypes + ", inputList=" + inputList + ", outputList="
                + outputList + '}';
    }

}
