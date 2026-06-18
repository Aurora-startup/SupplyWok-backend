package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Category;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.CategoryResource;

public class CategoryResourceFromEntityAssembler {

    public static CategoryResource toResourceFromEntity(Category entity) {
        return new CategoryResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}

