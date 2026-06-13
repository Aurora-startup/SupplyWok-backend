package aurora.supply_wok.platform.inventory.domain.model.queries;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.StockStatus;

public record GetAllItemsByFiltersQuery(String search, Long categoryId, Long supplierId, StockStatus status) {

    public GetAllItemsByFiltersQuery {
        if (categoryId != null && categoryId < 0)
        {
            throw new IllegalArgumentException("Category ID cannot be less than 0");
        }
        if (supplierId != null && supplierId < 0)
        {
            throw new IllegalArgumentException("Supplier ID cannot be less than 0");
        }
    }

}
