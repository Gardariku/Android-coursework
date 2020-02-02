package dietcourtserver.repository;

import dietcourtserver.model.Diet;
import dietcourtserver.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    Optional<Dish> findById(@Param("id") int id);

    Page<Dish> findAllByAppropriateDiets(Diet diet, Pageable pageable);

    List<Dish> findAllByAppropriateDiets(Diet diet);

    Page<Dish> findAll(Pageable pageable);

    Dish findTopByOrderByIdDesc();

    @Query("select d from Dish d JOIN d.composition c JOIN c.ingredient i WHERE i.id = :id ")
    Page<Dish> findAllByIngredientId(@Param("id") int id, Pageable pageable);

}
