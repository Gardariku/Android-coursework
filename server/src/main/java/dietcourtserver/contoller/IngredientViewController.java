package dietcourtserver.contoller;

import dietcourtserver.model.Diet;
import dietcourtserver.model.Ingredient;
import dietcourtserver.service.DietService;
import dietcourtserver.service.IngredientService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
public class IngredientViewController {

    private final IngredientService ingredientService;

    public IngredientViewController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Ingredient> getAll(Model model) {
        return ingredientService.getAll();
    }

    @RequestMapping(value = "/{ingredientid}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Ingredient> getIngredient(@PathVariable("ingredientid") int id, Model model) {
        return ingredientService.getIngredient(id);
    }
}
