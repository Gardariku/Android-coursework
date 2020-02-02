package dietcourtserver.repository;

import dietcourtserver.model.Dish;
import dietcourtserver.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    Set<Property> findAllByDishes(Dish dish);

}
