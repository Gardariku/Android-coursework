package dietcourtserver.service.impl;

import dietcourtserver.model.*;
import dietcourtserver.repository.CompositionsRepository;
import dietcourtserver.repository.DishRepository;
import dietcourtserver.repository.IngredientRepository;
import dietcourtserver.repository.PropertyRepository;
import dietcourtserver.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DishServiceImp implements DishService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;
    private final PropertyRepository propertyRepository;
    private final CompositionsRepository compositionsRepository;

    @Autowired
    public DishServiceImp(DishRepository dishRepository, IngredientRepository ingredientRepository,
                          PropertyRepository propertyRepository, CompositionsRepository compositionsRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
        this.propertyRepository = propertyRepository;
        this.compositionsRepository = compositionsRepository;
    }

    @Override
    public Dish findById(Integer id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Dish> findPage(int page) {
        return dishRepository.findAll(PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Page<Dish> findPageByDiet(Diet diet, int page) {
        return dishRepository.findAllByAppropriateDiets(diet, PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public List<Dish> findAllByDiet(Diet diet) {
        return dishRepository.findAllByAppropriateDiets(diet);
    }

    @Override
    public Page<Dish> findAllByIngredient(Ingredient ingredient, int page) {
        return dishRepository.findAllByIngredientId(ingredient.getId(), PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public Set<Ingredient> getAllIngredients(Dish dish) {
        Set<Ingredient> ingredients = new HashSet<>();
        for(Compositions component : dish.getComposition()) {
            ingredients.add(component.getIngredient());
        }
        return ingredients;
    }

    @Override
    public Set<Property> getAllProperties(Dish dish) {
        return propertyRepository.findAllByDishes(dish);
    }

    @Override
    public Dish save(Dish dish, MultipartFile file) throws IOException {
        try {
            if(file!= null && !file.isEmpty())
                dish.setAvatar(new javax.sql.rowset.serial.SerialBlob(file.getBytes())); }
        catch (SQLException e) {}

        return dishRepository.saveAndFlush(dish);
    }

    @Override
    public int getLastId() {
        return dishRepository.findTopByOrderByIdDesc().getId();
    }

    @Override
    public Compositions findCompByID(CompositionID id) {
        return compositionsRepository.findById(id).orElse(null);
    }

    @Override
    public Property findPropertyById(int id) {
        return propertyRepository.findById(id).orElse(null);
    }

    @Override
    public Ingredient findIngredientById(int id) {
        return ingredientRepository.findById(id).orElse(null);
    }


    @Override
    public void delete(Dish dish) {
        dishRepository.delete(dish);
    }


    private int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }

}
