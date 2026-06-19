package aurora.supply_wok.platform.inventory.interfaces.rest.resources;

import java.time.LocalDateTime;

public record StockMovementResource(
        Long id,
        Long itemId,
        Long supplierId,
        String type,
        double amount,
        String reason,
        LocalDateTime date
) {}