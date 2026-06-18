package aurora.supply_wok.platform.inventory.domain.model.commands;

import aurora.supply_wok.platform.inventory.domain.model.entities.Stock;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.UnitOfMeasure;

import java.util.List;

public record UpdateItemCommand(Long itemId, Long categoryId, SupplierId supplierId, RestaurantId restaurantId, String name, String brand, String imageUrl, UnitOfMeasure unitOfMeasure, Stock stock) {

    public UpdateItemCommand {

        if (itemId == null || itemId < 0) {
            throw new IllegalArgumentException("itemId cannot be null or empty");
        }
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null or empty");
        }
        if(stock.getMinimumStockLevel()>=stock.getMaximumStockLevel())
        {
            throw new IllegalArgumentException("Minimum stock level cannot be greater than maximum stock level");
        }
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
            throw new IllegalArgumentException("unitOfMeasure cannot be null");
        }
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be null");
        }
    }
}
