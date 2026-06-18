package aurora.supply_wok.platform.inventory.domain.services;

import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteAllInventoryActivitiesCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteAllStockMovementsCommand;

public interface InventoryCommandService {

    void handle(DeleteAllStockMovementsCommand command);

    void handle(DeleteAllInventoryActivitiesCommand command);
}