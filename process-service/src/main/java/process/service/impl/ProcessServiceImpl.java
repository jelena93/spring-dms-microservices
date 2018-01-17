package process.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import process.command.ProcessCmd;
import process.domain.Process;
import process.repository.ProcessRepository;
import process.service.ProcessService;

import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    ProcessRepository processRepository;

    @Override
    public Process save(Process process) {
        System.out.println(process);
        return processRepository.save(process);
    }

    @Override
    public void delete(Process process) {
        processRepository.delete(process);
    }

    @Override
    public Process findOne(Long id) {
        return processRepository.findOne(id);
    }

    @Override
    public List<Process> findByOwnerId(long ownerId) {
        return processRepository.findByOwnerId(ownerId);
    }

    @Override
    public void update(ProcessCmd processCmd, Process process) {
        if (processCmd.isPrimitive()) {
            deleteChildrenFromProcess(process);
        } else {
            process.getActivityList().clear();
        }
        processRepository.save(process);
    }

    private void deleteChildrenFromProcess(Process process) {
        System.out.println("deleting child from " + process.getName());
        List<Process> processes = processRepository.findByParent(process);
        processRepository.delete(processes);
    }
}
