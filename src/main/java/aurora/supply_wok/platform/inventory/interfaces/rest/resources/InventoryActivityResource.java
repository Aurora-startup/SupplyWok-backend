package aurora.supply_wok.platform.inventory.interfaces.rest.resources;

import java.time.LocalDateTime;

public record InventoryActivityResource(
        Long id,
        Long itemId,
        String type,
        String description,
        LocalDateTime date
) {}