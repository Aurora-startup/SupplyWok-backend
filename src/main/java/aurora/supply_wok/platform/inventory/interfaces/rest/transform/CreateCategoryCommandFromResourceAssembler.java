package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.commands.CreateCategoryCommand;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.CreateCategoryResource;

public class CreateCategoryCommandFromResourceAssembler {

    public static CreateCategoryCommand toCommandFromResource(CreateCategoryResource resource) {
        return new CreateCategoryCommand(
                resource.name(),
                resource.description()
        );
    }
}
