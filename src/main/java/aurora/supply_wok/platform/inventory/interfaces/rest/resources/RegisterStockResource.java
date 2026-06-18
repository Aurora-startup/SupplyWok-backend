package aurora.supply_wok.platform.inventory.interfaces.rest.resources;

public record RegisterStockResource(
        Long itemId,
        Long supplierId,
        String type,
        double quantity,
        String reason
) {}