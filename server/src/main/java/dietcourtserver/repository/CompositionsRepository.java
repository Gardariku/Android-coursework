package dietcourtserver.repository;

import dietcourtserver.model.CompositionID;
import dietcourtserver.model.Compositions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompositionsRepository extends JpaRepository<Compositions, CompositionID> {

    @Override
    Optional<Compositions> findById(CompositionID id);
}
