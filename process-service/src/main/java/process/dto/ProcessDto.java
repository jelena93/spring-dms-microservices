package process.dto;

public class ProcessDto {
    private Long id;
    private String name;
    private long ownerId;
    private ProcessDto parent;
    private boolean primitive;

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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public ProcessDto getParent() {
        return parent;
    }

    public void setParent(ProcessDto parent) {
        this.parent = parent;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    @Override
    public String toString() {
        return "ProcessDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", parent=" + parent +
                ", primitive=" + primitive +
                '}';
    }
}
