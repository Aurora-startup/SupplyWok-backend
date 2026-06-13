package aurora.supply_wok.platform.inventory.domain.model.valueobjects;

import java.time.LocalDateTime;

public record StockMovement (Long id, SupplierId supplierId, MovementType type, double amount, LocalDateTime date, String reason ){

    public StockMovement {

        if (id == null || id < 0)
        {
            throw new IllegalArgumentException("Movement ID cannot be null or less than zero");
        }
        if (type == null)
        {
            throw new IllegalArgumentException("Movement type cannot be null");
        }
        if (amount < 0)
        {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (reason == null || reason.isBlank())
        {
            throw new IllegalArgumentException("Reason cannot be empty");
        }

    }
}
