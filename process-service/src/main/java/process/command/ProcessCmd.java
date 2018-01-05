package process.command;

public class ProcessCmd {

    private String name;
    private String user;
    private Long parentId;
    private boolean primitive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        return "ProcessCmd{" + "name='" + name + '\'' + ", user='" + user + '\'' + ", parentId=" + parentId
                + ", primitive=" + primitive + '}';
    }
}
