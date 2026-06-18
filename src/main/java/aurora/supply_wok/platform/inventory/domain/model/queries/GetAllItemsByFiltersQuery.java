package aurora.supply_wok.platform.inventory.domain.model.queries;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.StockStatus;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;

public record GetAllItemsByFiltersQuery(String search, SupplierId supplierId, Long categoryId, StockStatus stockStatus) {

    public GetAllItemsByFiltersQuery {
        if (categoryId != null && categoryId < 0)
        {
            throw new IllegalArgumentException("Category ID cannot be less than 0");
        }
        if (supplierId != null && supplierId.supplierId() < 0)
        {
            throw new IllegalArgumentException("Supplier ID cannot be less than 0");
        }
    }

}
