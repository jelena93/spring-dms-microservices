package process.command;

public class ProcessCmd {

    private String name;
    private long ownerId;
    private Long parentId;
    private boolean primitive;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    @Override
    public String toString() {
        return "ProcessCmd{" + "name='" + name + '\'' + ", ownerId='" + ownerId + '\'' + ", parentId=" + parentId
                + ", primitive=" + primitive + '}';
    }
}
