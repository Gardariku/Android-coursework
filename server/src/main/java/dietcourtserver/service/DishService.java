package dietcourtserver.service;

import dietcourtserver.model.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DishService{

    Dish findById(Integer id);

    Page<Dish> findPage(int page);

    List<Dish> findAll();

    Page<Dish> findPageByDiet(Diet diet, int page);

    List<Dish> findAllByDiet(Diet diet);

    Page<Dish> findAllByIngredient(Ingredient ingredient, int page);

    Set<Ingredient> getAllIngredients(Dish dish);

    Set<Property> getAllProperties(Dish dish);

    Dish save(Dish dish, MultipartFile file) throws IOException;

    int getLastId();

    Compositions findCompByID(CompositionID id);

    Property findPropertyById(int id);

    Ingredient findIngredientById(int id);

    void delete(Dish dish);

}
