package descriptor.repository;

import descriptor.domain.DescriptorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptorTypeRepository extends JpaRepository<DescriptorType, Long> {

}
