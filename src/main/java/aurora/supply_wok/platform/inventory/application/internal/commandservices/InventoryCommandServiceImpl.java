package aurora.supply_wok.platform.inventory.application.internal.commandservices;

import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteAllInventoryActivitiesCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteAllStockMovementsCommand;
import aurora.supply_wok.platform.inventory.domain.services.InventoryCommandService;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryActivityRepository;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.StockMovementRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryCommandServiceImpl implements InventoryCommandService {

    private final StockMovementRepository stockMovementRepository;

    private final InventoryActivityRepository inventoryActivityRepository;

    public InventoryCommandServiceImpl(StockMovementRepository stockMovementRepository, InventoryActivityRepository inventoryActivityRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.inventoryActivityRepository = inventoryActivityRepository;
    }

    @Override
    public void handle(DeleteAllStockMovementsCommand command) {
        this.stockMovementRepository.deleteAll();
    }

    @Override
    public void handle(DeleteAllInventoryActivitiesCommand command) {
        this.inventoryActivityRepository.deleteAll();
    }
}
