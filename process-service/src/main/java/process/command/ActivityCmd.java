package process.command;

import java.util.ArrayList;
import java.util.List;

public class ActivityCmd {
    private String name;
    private Long processId;
    private List<Long> inputListDocumentTypes = new ArrayList<>();
    private List<Long> outputListDocumentTypes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
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

    @Override
    public String toString() {
        return "ActivityCmd{" + "name='" + name + '\'' + ", processId=" + processId + ", inputListDocumentTypes="
                + inputListDocumentTypes + ", outputListDocumentTypes=" + outputListDocumentTypes + '}';
    }
}
