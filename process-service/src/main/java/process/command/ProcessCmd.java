package process.command;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ProcessCmd {
    @NotEmpty(message = "{validation.process.name.empty}")
    @NotNull(message = "{validation.process.name.null}")
    private String name;

    @NotNull(message = "{validation.process.ownerId.null}")
    private Long ownerId;

    private Long parentId;

    private boolean primitive;

    public String getName() {
        return name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    @Override
    public String toString() {
        return "ProcessCmd{" + "name='" + name + '\'' + ", ownerId='" + ownerId + '\'' + ", parentId=" + parentId
                + ", primitive=" + primitive + '}';
    }
}
