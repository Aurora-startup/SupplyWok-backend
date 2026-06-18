package aurora.supply_wok.platform.inventory.application.internal.queryservices;

import aurora.supply_wok.platform.inventory.application.queryservices.CategoryQueryService;
import aurora.supply_wok.platform.inventory.domain.model.aggregates.Category;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllCategoriesQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllCategoriesWithNumberOfItemsQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetCategoryByIdQuery;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.CategoryPersistenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryPersistenceRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryPersistenceRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> handle(GetAllCategoriesQuery query) {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Category> handle(GetAllCategoriesWithNumberOfItemsQuery query) {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> handle(GetCategoryByIdQuery query) {
        return this.categoryRepository.findById(query.categoryId());
    }
}
