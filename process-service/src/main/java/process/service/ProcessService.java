package process.service;

import process.command.ProcessCmd;
import process.domain.Process;

import java.util.List;

public interface ProcessService {

    Process save(Process process);

    void delete(Process child);

    Process findOne(Long id);

    List<Process> findByUser(String user);

    void update(ProcessCmd processCmd, Process process);
}
