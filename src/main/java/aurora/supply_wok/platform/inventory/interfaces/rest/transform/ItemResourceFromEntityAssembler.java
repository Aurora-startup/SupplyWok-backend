package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.ItemResource;

public class ItemResourceFromEntityAssembler {
    public static ItemResource toResourceFromEntity(Item entity) {
        return new ItemResource(
                entity.getId(),
                entity.getCategoryId(),
                entity.getRestaurantId().restaurantId(),
                entity.getSupplierId().supplierId(),
                entity.getName(),
                entity.getBrand(),
                entity.getUnitOfMeasure().name(),
                entity.getImageUrl(),
                entity.getStock().getCurrentStock()
        );
    }
}
