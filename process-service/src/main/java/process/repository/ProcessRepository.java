/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import process.domain.Process;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findByParentIsNull();

    Process findByParent(Process parent);

    List<Process> findByUser(String user);
}
