package process.command;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ActivityCmd {
    @NotEmpty(message = "{validation.activity.name.empty}")
    @NotNull(message = "{validation.activity.name.null}")
    private String name;

    private Long processId;

    @NotEmpty(message = "{validation.activity.inputListDocumentTypes.empty}")
    private List<Long> inputListDocumentTypes = new ArrayList<>();

    @NotEmpty(message = "{validation.activity.outputListDocumentTypes.empty}")
    private List<Long> outputListDocumentTypes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Long getProcessId() {
        return processId;
    }

    public List<Long> getInputListDocumentTypes() {
        return inputListDocumentTypes;
    }

    public List<Long> getOutputListDocumentTypes() {
        return outputListDocumentTypes;
    }

    @Override
    public String toString() {
        return "ActivityCmd{" + "name='" + name + '\'' + ", processId=" + processId + ", inputListDocumentTypes="
                + inputListDocumentTypes + ", outputListDocumentTypes=" + outputListDocumentTypes + '}';
    }
}
