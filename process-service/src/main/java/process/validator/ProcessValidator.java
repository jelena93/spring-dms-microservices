package process.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import process.command.ProcessCmd;
import process.domain.Process;
import process.service.ProcessService;

import java.util.List;

@Component
public class ProcessValidator {

    private final ProcessService processService;

    @Autowired
    public ProcessValidator(ProcessService processService) {
        this.processService = processService;
    }

    public void validate(ProcessCmd processCmd) throws Exception {
        List<Process> byName = processService.findByNameAndOwnerId(processCmd.getName(), processCmd.getOwnerId());
        if (!byName.isEmpty()) {
            throw new Exception("Process with same name already exists");
        }
    }
}
