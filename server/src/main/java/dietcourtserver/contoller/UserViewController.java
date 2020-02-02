package dietcourtserver.contoller;

import dietcourtserver.model.*;
import dietcourtserver.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserViewController {

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<User> getUser(@PathVariable("userid") int id, Model model) {
        return userService.findById(id);
    }

    @RequestMapping(value="/registrate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public User registrateUser(@RequestBody User user, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return null;
        } else {
            //int id = dishService.getLastId()+1;

            userService.save(user, null);
            return user;
        }
    }

    @RequestMapping(value="/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public User updateUser(@RequestBody User user, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return null;
        } else {
            //int id = dishService.getLastId()+1;

            userService.save(user, null);
            return user;
        }
    }

    @RequestMapping(value="/auth", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public User registrateUser(@RequestBody HashMap<String, String> auth, BindingResult bindingResult, Model model) throws Exception {
        User user = userService.checkAuth(auth.get("name"), auth.get("password"));
        if(user == null)
            bindingResult.addError(new ObjectError("Auth error", "No such user"));

        if (bindingResult.hasErrors()) {
            return null;
        } else {
            return user;
        }
    }

    @RequestMapping(value="/calculate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public User calcNeeds(@RequestBody HashMap<String, String> auth, BindingResult bindingResult, Model model) throws Exception {
        User user = userService.checkAuth(auth.get("name"), auth.get("password"));
        if(user == null)
            bindingResult.addError(new ObjectError("Auth error", "No such user"));

        if (bindingResult.hasErrors()) {
            return null;
        } else {
            user.calculateRecommendedCalories();
            user.calculateRecommendedFats();
            user.calculateRecommendedCarbohydrates();
            user.calculateRecommendedProteins();
            userService.save(user, null);
            return user;
        }
    }
}
