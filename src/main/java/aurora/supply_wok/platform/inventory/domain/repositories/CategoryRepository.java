package aurora.supply_wok.platform.inventory.domain.repositories;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Category;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Long id);

    List<Category> findAll();

    Category save(Category category);

    boolean existsByName(String name);

    void deleteById(Long id);
}
