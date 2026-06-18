package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.commands.CreateItemCommand;
import aurora.supply_wok.platform.inventory.domain.model.entities.Stock;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.UnitOfMeasure;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.CreateItemResource;

public class CreateItemCommandFromResourceAssembler {
    public static CreateItemCommand toCommandFromResource(CreateItemResource resource) {
        return new CreateItemCommand(
                resource.categoryId(),
                new RestaurantId(resource.restaurantId()),
                new SupplierId(resource.supplierId()),
                resource.name(),
                resource.brand(),
                UnitOfMeasure.valueOf(resource.unitOfMeasure()),
                resource.imageUrl(),
                new Stock(resource.stock(), 0,0)
        );
    }
}

