package aurora.supply_wok.platform.inventory.application.queryservices;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllItemsByFiltersQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllItemsQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetItemByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ItemQueryService {
    List<Item> handle(GetAllItemsQuery query);
    List<Item> handle(GetAllItemsByFiltersQuery query);
    Optional<Item> handle(GetItemByIdQuery query);
}
