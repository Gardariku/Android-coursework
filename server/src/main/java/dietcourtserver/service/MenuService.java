package dietcourtserver.service;

import dietcourtserver.model.Menu;
import dietcourtserver.model.User;

import java.util.List;

public interface MenuService {

    List<Menu> getAll(User user, int number);

}
