package aurora.supply_wok.platform.inventory.application.queryservices;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Category;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllCategoriesQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllCategoriesWithNumberOfItemsQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetCategoryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryService {
    List<Category> handle(GetAllCategoriesQuery query);
    List<Category> handle(GetAllCategoriesWithNumberOfItemsQuery query);
    Optional<Category> handle(GetCategoryByIdQuery query);
}
