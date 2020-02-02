package dietcourtserver.service.impl;

import dietcourtserver.model.Diet;
import dietcourtserver.model.Property;
import dietcourtserver.repository.DietRepository;
import dietcourtserver.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DietServiceImp implements DietService {

    private  final DietRepository dietRepository;

    @Autowired
    public DietServiceImp(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    @Override
    public List<Diet> getAll() {
        return dietRepository.findAll();
    }

    @Override
    public Diet findById(int id) {
        return dietRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Property> getBannedProperties(Diet diet) {
        return diet.getBannedProperties();
    }
}
