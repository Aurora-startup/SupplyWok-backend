package aurora.supply_wok.platform.inventory.application.internal.commandservices;

import aurora.supply_wok.platform.inventory.application.commandservices.InventoryCommandService;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteAllInventoryActivitiesCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteAllStockMovementsCommand;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryActivityPersistenceRepository;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.StockMovementPersistenceRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryCommandServiceImpl implements InventoryCommandService {

    private final StockMovementPersistenceRepository stockMovementRepository;

    private final InventoryActivityPersistenceRepository inventoryActivityRepository;

    public InventoryCommandServiceImpl(StockMovementPersistenceRepository stockMovementRepository,
                                       InventoryActivityPersistenceRepository inventoryActivityRepository) {
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
