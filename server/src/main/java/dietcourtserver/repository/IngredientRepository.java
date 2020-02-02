package dietcourtserver.repository;

import dietcourtserver.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}
