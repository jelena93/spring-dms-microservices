package process.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import process.command.ProcessCmd;
import process.domain.Process;
import process.service.ProcessService;

public abstract class ProcessMapperDecorator implements ProcessMapper {
    @Autowired
    ProcessMapper delegate;
    @Autowired
    ProcessService processService;

    @Override
    public Process mapToEntity(ProcessCmd processCmd) {
        Process process = delegate.mapToEntity(processCmd);
        if (processCmd.getParentId() != null) {
            Process parent = processService.findOne(processCmd.getParentId());
            process.setParent(parent);
        }
        return process;
    }
}
