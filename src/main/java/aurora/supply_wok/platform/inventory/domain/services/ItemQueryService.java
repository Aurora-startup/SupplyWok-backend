package aurora.supply_wok.platform.inventory.domain.services;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ItemQueryService {
    List<Item> handle(GetAllItemsQuery query);
    List<Item> handle(GetAllItemsByFiltersQuery query);
    Optional<Item> handle(GetItemByIdQuery query);

}