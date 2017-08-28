/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.repository;

import document.domain.Descriptor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptorRepository extends JpaRepository<Descriptor, Long> {

    List<Descriptor> findByDocumentType(Long documentType);
}
