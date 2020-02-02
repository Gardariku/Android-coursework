package dietcourtserver.service.impl;

import dietcourtserver.model.Diet;
import dietcourtserver.model.Menu;
import dietcourtserver.model.User;
import dietcourtserver.repository.MenuRepository;
import dietcourtserver.repository.UserRepository;
import dietcourtserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, MenuRepository menuRepository) {
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> findAll(int page) {
        return userRepository.findAll(PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public User save(User user, MultipartFile file) throws IOException {
        /*
        // Кодирование пароля
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Установка роли Пользователь
        user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));

        try {
            if(file!= null && !file.isEmpty())
                user.setAvatar(new javax.sql.rowset.serial.SerialBlob(file.getBytes())); }
        catch (SQLException e) {}
        */

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User checkAuth(String name, String pass) {
        return userRepository.findByNameAndPassword(name, pass);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<Menu> getMenu(User user) {
        return menuRepository.findAllByUser(user);
    }

    @Override
    public List<Menu> getContradictions(User user) {
        List<Menu> menu = user.getIngestions();
        List<Menu> contradictions = new ArrayList<>();

        for(Menu dish: menu) {
            if(!dish.getDish().isAllowedTo(user))
                contradictions.add(dish);
        }

        return contradictions;
    }

    private int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }
}
