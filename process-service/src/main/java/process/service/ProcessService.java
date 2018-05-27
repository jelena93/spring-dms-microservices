package process.service;

import process.command.ProcessCmd;
import process.domain.Process;

import java.util.List;

public interface ProcessService {

    Process save(Process process);

    void delete(Process child);

    Process findOne(Long id);

    List<Process> findByOwnerId(long ownerId);

    Process update(ProcessCmd processCmd, Process process);

    List<Process> findByParent(Process parent);

    List<Process> findByNameAndOwnerId(String name, long ownerId);

}
