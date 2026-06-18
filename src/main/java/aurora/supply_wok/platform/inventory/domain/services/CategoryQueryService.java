package aurora.supply_wok.platform.inventory.domain.services;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Category;
import aurora.supply_wok.platform.inventory.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryService {
    List<Category> handle(GetAllCategoriesQuery query);
    List<Category> handle(GetAllCategoriesWithNumberOfItemsQuery query);
    Optional<Category> handle(GetCategoryByIdQuery query);
}