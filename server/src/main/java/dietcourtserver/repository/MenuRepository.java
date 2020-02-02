package dietcourtserver.repository;

import dietcourtserver.model.Menu;
import dietcourtserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByUser(User user);

}
