package dietcourtserver.service;

import dietcourtserver.model.Diet;
import dietcourtserver.model.Property;

import java.util.List;
import java.util.Set;

public interface DietService {

    List<Diet> getAll();

    Diet findById(int id);

    Set<Property> getBannedProperties(Diet diet);

}
