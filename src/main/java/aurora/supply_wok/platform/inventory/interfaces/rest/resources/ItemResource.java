package aurora.supply_wok.platform.inventory.interfaces.rest.resources;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.UnitOfMeasure;

public record ItemResource(
        Long id,
        Long categoryId,
        Long restaurantId,
        Long supplierId,
        String name,
        String brand,
        String unitOfMeasure,
        String imageUrl,
        double stock
) {}
