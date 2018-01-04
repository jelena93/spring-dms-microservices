package process.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import process.domain.Process;
import process.repository.ProcessRepository;
import process.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    ProcessRepository processRepository;

    @Override
    public Process save(Process process) {
        return processRepository.save(process);
    }
}
