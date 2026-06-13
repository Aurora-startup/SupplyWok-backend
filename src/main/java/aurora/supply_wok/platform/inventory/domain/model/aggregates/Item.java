package aurora.supply_wok.platform.inventory.domain.model.aggregates;

import aurora.supply_wok.platform.inventory.domain.model.entities.Stock;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.*;
import aurora.supply_wok.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Item extends AuditableAbstractAggregateRoot<Item> {

    private Long id;
    private Long categoryId;
    private RestaurantId restaurantId;
    private List<SupplierId> supplierIds;
    private String name;
    private String brand;
    private UnitOfMeasure unitOfMeasure;
    private String imageUrl;
    private Stock stock;

    public Item(Long categoryId, RestaurantId restaurantId, List<SupplierId> supplierIds, String name, String brand, UnitOfMeasure unitOfMeasure, String imageUrl, Stock stock) {

        if (restaurantId == null || restaurantId.restaurantId() == null || restaurantId.restaurantId() < 0) {
            throw new IllegalArgumentException("RestaurantId cannot be null or less than 0");
        }
        if (supplierIds == null || supplierIds.isEmpty()) {
            throw new IllegalArgumentException("SupplierIds cannot be null or empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
        if (unitOfMeasure == null) {
            throw new IllegalArgumentException("UnitOfMeasure cannot be null");
        }
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        this.categoryId = categoryId;
        this.restaurantId = restaurantId;
        this.supplierIds = supplierIds;
        this.name = name;
        this.brand = brand;
        this.unitOfMeasure = unitOfMeasure;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public void updateInformation(Long categoryId, String name, String brand, UnitOfMeasure unitOfMeasure, String imageUrl)
    {
        this.name = name;
        this.brand = brand;
        this.categoryId = categoryId;
        this.unitOfMeasure = unitOfMeasure;
        this.imageUrl = imageUrl;
    }

    public void increaseStock(double quantity)
    {
        this.stock.increaseStock(quantity);
    }

    public void decreaseStock(double quantity)
    {
        this.stock.decreaseStock(quantity);
    }

    public void adjustStock(double quantity)
    {
        this.stock.adjustCurrentStock(quantity);
    }

    public void updateStockLevels(double minimum, double maximum)
    {
        if (minimum > maximum)
        {
            throw new IllegalArgumentException("Minimum level cannot be greater than maximum level");
        }
        if (minimum < 0 || maximum <= 0)
        {
            throw new IllegalArgumentException("Minimum and maximum levels must be greater than zero");
        }
        this.stock.changeMaximumLevel(maximum);
        this.stock.changeMinimumLevel(minimum);
    }

    public void addSupplier(SupplierId supplierId)
    {
        if (supplierId != null && supplierIds.contains(supplierId)) {
            throw new IllegalArgumentException("SupplierId already exist in the item");
        }
        if (supplierId == null) {
            throw new IllegalArgumentException("SupplierId cannot be null");
        }
        this.supplierIds.add(supplierId);
    }

    public StockMovement registerEntry(double quantity, SupplierId supplierId, String reason, LocalDateTime date)
    {
        if (supplierId == null) throw new IllegalArgumentException("Supplier required for entries");
        if (!supplierIds.contains(supplierId)) throw new IllegalArgumentException("Supplier not authorized");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason required");

        this.stock.increaseStock(quantity); // Delega a tu entidad Stock

        return new StockMovement(null, supplierId, MovementType.ENTRY, quantity, date, reason);
    }

    public StockMovement registerExit(double quantity, String reason, LocalDateTime date, MovementType type) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason required");

        this.stock.decreaseStock(quantity);

        return new StockMovement(null, null, type, quantity, date, reason);
    }

    public StockMovement registerAdjustment(double currentPhysicalStock, String reason, LocalDateTime date) {
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason required");

        double previousStock = this.stock.getCurrentStock();
        double difference = currentPhysicalStock - previousStock;

        this.stock.adjustCurrentStock(currentPhysicalStock);

        return new StockMovement(null, null, MovementType.ADJUSTMENT, difference, date, reason);
    }

}
