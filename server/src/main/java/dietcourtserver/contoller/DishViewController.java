package dietcourtserver.contoller;

import dietcourtserver.model.*;
import dietcourtserver.service.DietService;
import dietcourtserver.service.DishService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishViewController {

    private final DishService dishService;
    private final DietService dietService;

    public DishViewController(DishService dishService, DietService dietService) {
        this.dishService = dishService; this.dietService = dietService;
    }

    @RequestMapping(value = "/{dishid}", method = RequestMethod.GET)
    @ResponseBody
    public Dish getDish(@PathVariable("dishid") int id, Model model) {
        return dishService.findById(id);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public Page<Dish> getDishesPage(@RequestParam(defaultValue = "0") int page, Model model) {
        return dishService.findPage(1);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Dish> getAllDishes(Model model) {
        return dishService.findAll();
    }

    @RequestMapping(value="/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public String addNewDish(@RequestBody Dish dish, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "error";
        } else {
            dish.setId(0);
            Dish newDish = new Dish();
            model.addAttribute("dish", newDish);

            newDish.setName(dish.getName());
            newDish.setRecipe(dish.getRecipe());
            newDish.setType(dish.getType());
            newDish.setAppropriateDiets(new HashSet<>());
            newDish.setComposition(new ArrayList<>());
            newDish.setProperties(new HashSet<>());
            newDish.setIngestions(new ArrayList<>());

            int id = dishService.getLastId()+1;

            newDish.getComposition().add(new Compositions(new CompositionID(id, 1), 100));
            for(Compositions c: dish.getComposition()) {
                newDish.getComposition().add(new Compositions(new CompositionID(id, c.getIngredient().getId()), c.getQuantity()));
            }
            for(Property p: dish.getProperties()) {
                newDish.getProperties().add(dishService.findPropertyById(p.getId()));
            }
            /*
            for(Diet d: dish.getAppropriateDiets()) {
                newDish.getAppropriateDiets().add(dietService.findById(d.getId()));
            }
            */

            dishService.save(newDish, null);
            return "success after adding " + dish.getName();
        }
    }

}
