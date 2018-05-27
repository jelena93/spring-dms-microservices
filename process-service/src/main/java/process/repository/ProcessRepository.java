package process.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import process.domain.Process;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findByParent(Process parent);

    List<Process> findByOwnerId(long ownerId);

    List<Process> findByNameAndOwnerId(String name, long ownerId);

}
