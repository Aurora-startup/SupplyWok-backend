package aurora.supply_wok.platform.inventory.domain.model.entities;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.StockStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
@Embeddable
public class Stock {

    @Column(name = "current_stock", nullable = false)
    private double currentStock;

    @Min(0)
    @Column(name = "max_stock_level", nullable = false)
    private double maximumStockLevel;

    @Min(0)
    @Column(name = "min_stock_level", nullable = false)
    private double minimumStockLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_status", nullable = false)
    private StockStatus stockStatus;

    public Stock() {

    }

    public Stock(double currentStock, double maximumStockLevel, double minimumStockLevel)
    {
        this.currentStock = currentStock;
        this.maximumStockLevel = maximumStockLevel;
        this.minimumStockLevel = minimumStockLevel;
        updateStockStatus();
    }

    public void updateStockStatus() {

        double range = maximumStockLevel - minimumStockLevel;
        double percent = minimumStockLevel + (range * 0.25);

        if (currentStock < minimumStockLevel) {
            stockStatus = StockStatus.NEED_REFILL;

        } else if(currentStock > maximumStockLevel) {
            stockStatus = StockStatus.OVER_STOCK;

        } else if(currentStock > percent) {
            stockStatus = StockStatus.GOOD_STOCK;

        } else if(currentStock <= percent) {
            stockStatus = StockStatus.LOW_STOCK;
        }
    }

    public void increaseStock(double quantity)
    {
        if (quantity <= 0)
        {
            throw  new IllegalArgumentException("Quantity must be greater than zero");
        }
        currentStock += quantity;
        updateStockStatus();
    }

    public void decreaseStock(double quantity)
    {
        if (quantity <= 0)
        {
            throw  new IllegalArgumentException("Quantity must be greater than zero");
        }
        if(quantity > currentStock)
        {
            throw  new IllegalArgumentException("Quantity to decrease exceeds current stock");
        }
        currentStock -= quantity;
        updateStockStatus();
    }

    public void adjustCurrentStock(double quantity)
    {
        if (quantity < 0)
        {
             throw new IllegalArgumentException("Item stock must be greater than or equal to zero");
        }
        currentStock = quantity;
        updateStockStatus();
    }

    public void changeMaximumLevel(double level)
    {
        if (level <= 0)
        {
            throw new IllegalArgumentException("Maximum level must be greater than zero");
        }
        maximumStockLevel = level;
        updateStockStatus();
    }

    public void changeMinimumLevel(double level)
    {
        if (level <= 0)
        {
            throw new IllegalArgumentException("Minimum level must be greater than or equal to zero");
        }
        minimumStockLevel = level;
        updateStockStatus();
    }

}
