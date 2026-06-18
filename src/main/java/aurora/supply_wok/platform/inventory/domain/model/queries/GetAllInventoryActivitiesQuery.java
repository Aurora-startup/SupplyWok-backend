package aurora.supply_wok.platform.inventory.domain.model.queries;

public record GetAllInventoryActivitiesQuery(int page, int size) {
    public GetAllInventoryActivitiesQuery {
        if (page < 0) {
            throw new IllegalArgumentException("Page cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
    }
}
