package descriptor.repository;

import descriptor.domain.Descriptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface DescriptorRepository extends JpaRepository<Descriptor, Long> {
    //    @Transactional
    //    void deleteByDocumentIdIn(List<Long> documentIds);

    @Modifying
    @Transactional
    @Query("delete from Descriptor d where d.documentId in ?1")
    void deleteByDocumentIdIn(List<Long> ids);
}
