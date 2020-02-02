package dietcourtserver.service;

import dietcourtserver.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    Optional<Ingredient> getIngredient(int id);

    List<Ingredient> getAll();

}
