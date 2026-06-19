package aurora.supply_wok.platform.inventory.domain.model.commands;

import aurora.supply_wok.platform.inventory.domain.model.entities.Stock;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.UnitOfMeasure;

public record CreateItemCommand (Long categoryId, RestaurantId restaurantId, SupplierId supplierId, String name, String brand, UnitOfMeasure unitOfMeasure, String imageUrl, Stock stock) {

    public CreateItemCommand {
        if (restaurantId == null) {
            throw new IllegalArgumentException("restaurantId cannot be null");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        if (brand == null || brand.isEmpty()) {
            throw new IllegalArgumentException("brand cannot be null or empty");
        }
        if (unitOfMeasure == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be null");
        }
    }
}
