package process.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "process")
public class Process implements Serializable {

    private static final long serialVersionUID = 5211102732754088501L;

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
    @NotEmpty
    private String name;

    @NotNull
    @Column(name = "owner_id")
    private long ownerId;

    @JoinColumn(name = "parent")
    @ManyToOne
    private Process parent;

    @Column(name = "primitive")
    private boolean primitive;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "process_id")
    private List<Activity> activityList = new ArrayList<>();

    public Process() { }

    public Process(String name, Process parent, boolean primitive, long ownerId) {
        this.name = name;
        this.parent = parent;
        this.primitive = primitive;
        this.ownerId = ownerId;
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

    public Process getParent() {
        return parent;
    }

    public void setParent(Process parent) {
        this.parent = parent;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
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
        final Process other = (Process) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Process{" + "id=" + id + ", name='" + name + '\'' + ", ownerId='" + ownerId + '\'' + ", parent="
                + parent + ", primitive=" + primitive + ", activityList=" + activityList + '}';
    }
}
