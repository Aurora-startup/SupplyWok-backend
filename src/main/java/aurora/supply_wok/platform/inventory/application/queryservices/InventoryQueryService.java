package aurora.supply_wok.platform.inventory.application.queryservices;

import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllInventoryActivitiesQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllStockMovementsByItemIdQuery;

import java.util.List;

public interface InventoryQueryService {

    List<StockMovement> handle(GetAllStockMovementsByItemIdQuery query);

    List<InventoryActivity> handle(GetAllInventoryActivitiesQuery query);
}
