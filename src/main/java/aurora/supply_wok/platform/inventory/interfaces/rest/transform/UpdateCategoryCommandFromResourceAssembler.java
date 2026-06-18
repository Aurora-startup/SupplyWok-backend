package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateCategoryCommand;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.CategoryResource;

public class UpdateCategoryCommandFromResourceAssembler {
    public static UpdateCategoryCommand toCommandFromResource (Long categoryId, CategoryResource resource) {
        return new UpdateCategoryCommand(categoryId, resource.name(), resource.description());
    }
}