package process.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import process.domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
