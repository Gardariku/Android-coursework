package dietcourtserver.service.impl;

import dietcourtserver.model.Menu;
import dietcourtserver.model.User;
import dietcourtserver.repository.MenuRepository;
import dietcourtserver.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuServiceImp implements MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImp(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> getAll(User user, int number) {
        return menuRepository.findAllByUser(user);
    }

    public List<Menu> getContradictions() {
        return null;
    }

}
