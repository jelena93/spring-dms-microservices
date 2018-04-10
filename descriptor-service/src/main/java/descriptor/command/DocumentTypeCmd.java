package descriptor.command;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class DocumentTypeCmd {
    @NotNull(message = "{validation.documentType.name.null}")
    @NotEmpty(message = "{validation.documentType.name.empty}")
    private String name;

    @NotEmpty(message = "{validation.descriptors.empty}")
    private List<DescriptorCmd> descriptors = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<DescriptorCmd> getDescriptors() {
        return descriptors;
    }

    @Override
    public String toString() {
        return "DocumentTypeCmd{" +
                "name='" + name + '\'' +
                ", descriptors=" + descriptors +
                '}';
    }
}
