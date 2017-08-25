/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jelenas
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByDescriptorsDescriptorKey(String descriptorKey);

    List<Document> findByCompanyId(String fileName);

    List<Document> findByFileName(String fileName);
}
