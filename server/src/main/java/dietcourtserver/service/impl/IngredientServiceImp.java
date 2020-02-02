package dietcourtserver.service.impl;

import dietcourtserver.model.Ingredient;
import dietcourtserver.repository.IngredientRepository;
import dietcourtserver.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientService {

    private  final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImp(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public Optional<Ingredient> getIngredient(int id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }
}
