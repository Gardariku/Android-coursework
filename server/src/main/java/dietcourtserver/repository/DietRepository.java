package dietcourtserver.repository;

import dietcourtserver.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Integer> {
}
