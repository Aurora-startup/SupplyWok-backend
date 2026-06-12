package aurora.supply_wok.platform.inventory.domain.model.entities;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.StockStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Stock {

    @Setter
    private Long id;

    @Setter
    private Long itemId;

    @Setter
    private double currentStock;

    @Setter
    private double maximumStockLevel;

    @Setter
    private double minimumStockLevel;

    @Setter
    private StockStatus stockStatus;

    @Setter
    private Long stockMovementHistoryId;

    public Stock(double currentStock, double maximumStockLevel, double minimumStockLevel,
     Long stockMovementHistoryId)
    {
        this.currentStock = currentStock;
        this.maximumStockLevel = maximumStockLevel;
        this.minimumStockLevel = minimumStockLevel;
        this.stockMovementHistoryId = stockMovementHistoryId;
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

    public void increaseStock(int quantity)
    {
        if (quantity <= 0)
        {
            throw  new IllegalArgumentException("Quantity must be greater than zero");
        }
        currentStock += quantity;
        updateStockStatus();
    }

    public void decreaseStock(int quantity)
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

    public void adjustCurrentStock(int quantity)
    {
        if (quantity < 0)
        {
             throw new IllegalArgumentException("Item stock must be greater than or equal to zero");
        }
        currentStock = quantity;
        updateStockStatus();
    }

    public void changeMaximumLevel(int level)
    {
        if (level <= 0)
        {
            throw new IllegalArgumentException("Maximum level must be greater than zero");
        }
        maximumStockLevel = level;
        updateStockStatus();
    }

    public void changeMinimumLevel(int level)
    {
        if (level <= 0)
        {
            throw new IllegalArgumentException("Minimum level must be greater than or equal to zero");
        }
        minimumStockLevel = level;
        updateStockStatus();
    }



}
