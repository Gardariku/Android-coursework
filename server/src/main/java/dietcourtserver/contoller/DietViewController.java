package dietcourtserver.contoller;

import dietcourtserver.model.Diet;
import dietcourtserver.model.Dish;
import dietcourtserver.service.DietService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/diet")
public class DietViewController {

    private final DietService dietService;

    public DietViewController(DietService dietService) {
        this.dietService = dietService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Diet> getAllDiets(Model model) {
        return dietService.getAll();
    }

    @RequestMapping(value = "/{dietid}", method = RequestMethod.GET)
    @ResponseBody
    public Diet getDiet(@PathVariable("dietid") int id, Model model) {
        return dietService.findById(id);
    }

}
