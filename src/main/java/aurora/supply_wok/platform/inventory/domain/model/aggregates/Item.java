package aurora.supply_wok.platform.inventory.domain.model.aggregates;

import aurora.supply_wok.platform.inventory.domain.model.entities.Stock;
import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.*;
import aurora.supply_wok.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Item extends AuditableAbstractAggregateRoot<Item> {

    @Getter
    @Column(name = "category_id", nullable = true)
    private Long categoryId;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "restaurantId", column = @Column(name = "restaurant_id", nullable = false))
    })
    private RestaurantId restaurantId;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "supplierId", column = @Column(name = "supplier_id", nullable = false))
    })
    private SupplierId supplierId;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "name", length = 70, nullable = false)
    private String name;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "brand", length = 50, nullable = false)
    private String brand;

    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", nullable = false)
    private UnitOfMeasure unitOfMeasure;

    @Getter
    @Column(name = "image_url", length = 255, nullable = true)
    private String imageUrl;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currentStock", column = @Column(name = "current_stock", nullable = false)),
            @AttributeOverride(name = "maximumStockLevel", column = @Column(name = "max_stock_level", nullable = false)),
            @AttributeOverride(name = "minimumStockLevel", column = @Column(name = "min_stock_level", nullable = false)),
            @AttributeOverride(name = "stockStatus", column = @Column(name = "stock_status", nullable = false))
    })
    private Stock stock;

    public Item(Long categoryId, RestaurantId restaurantId, SupplierId supplierId, String name, String brand, UnitOfMeasure unitOfMeasure, String imageUrl, Stock stock) {

        this.categoryId = categoryId;
        this.restaurantId = restaurantId;
        this.supplierId = supplierId;
        this.name = name;
        this.brand = brand;
        this.unitOfMeasure = unitOfMeasure;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public Item() {

    }

    public void updateInformation(Long categoryId, SupplierId supplierId, String name, String brand, UnitOfMeasure unitOfMeasure, String imageUrl, Stock stock)
    {
        this.supplierId = supplierId;
        this.name = name;
        this.brand = brand;
        this.categoryId = categoryId;
        this.unitOfMeasure = unitOfMeasure;
        this.imageUrl = imageUrl;
        this.stock.changeMaximumLevel(stock.getMaximumStockLevel());
        this.stock.changeMinimumLevel(stock.getMinimumStockLevel());
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

    public void registerEntry(double quantity, SupplierId supplierId, String reason, LocalDateTime date)
    {
        if (supplierId == null) throw new IllegalArgumentException("Supplier required for entries");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason required");

        this.stock.increaseStock(quantity);

    }

    public void registerExit(double quantity, String reason, LocalDateTime date, MovementType type) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason required");

        this.stock.decreaseStock(quantity);

    }

    public void registerAdjustment(double currentPhysicalStock, String reason, LocalDateTime date) {
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason required");

        double previousStock = this.stock.getCurrentStock();
        double difference = currentPhysicalStock - previousStock;

        this.stock.adjustCurrentStock(currentPhysicalStock);

    }

}
