package dietcourtserver.service;

import dietcourtserver.model.Menu;
import dietcourtserver.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(int id);

    Page<User> findAll(int page);

    User save(User user, MultipartFile file) throws IOException;

    User checkAuth(String name, String pass);

    void delete(User user);

    List<Menu> getMenu(User user);

    List<Menu> getContradictions(User user);

}
