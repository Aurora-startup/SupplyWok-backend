package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateItemCommand;
import aurora.supply_wok.platform.inventory.domain.model.entities.Stock;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.UnitOfMeasure;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.ItemResource;

public class UpdateItemCommandFromResourceAssembler {
    public static UpdateItemCommand toCommandFromResource(Long itemId, ItemResource resource) {
        var stock = new Stock(
                resource.stock().currentStock(),
                resource.stock().maxLevel(),
                resource.stock().minLevel()
        );

        return new UpdateItemCommand(
                itemId,
                resource.categoryId(),
                new SupplierId(resource.supplierId()),
                new RestaurantId(resource.restaurantId()),
                resource.name(),
                resource.brand(),
                resource.imageUrl(),
                UnitOfMeasure.valueOf(resource.unitOfMeasure()),
                stock
        );
    }
}

