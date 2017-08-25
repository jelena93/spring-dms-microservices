/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.List;
import org.dom4j.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

    @Query("SELECT d FROM DocumentType d WHERE d.id IN :ids")
    List<DocumentType> findById(@Param("ids") List<Long> ids);
}
