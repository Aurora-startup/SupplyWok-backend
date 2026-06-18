package aurora.supply_wok.platform.inventory.domain.model.queries;

public record GetAllStockMovementsByItemIdQuery(Long itemId) {

    public GetAllStockMovementsByItemIdQuery {

        if (itemId == null || itemId < 0) {
            throw new IllegalArgumentException("Invalid item id");
        }
    }
}
