package descriptor.repository;

import descriptor.domain.Descriptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DescriptorRepository extends JpaRepository<Descriptor, Long> {

    @Modifying
    @Transactional
    @Query("delete from Descriptor d where d.documentId in ?1")
    void deleteByDocumentIdIn(List<Long> ids);

    List<Descriptor> findByDocumentTypeIdAndDocumentIdIsNull(long documentTypeId);
}
