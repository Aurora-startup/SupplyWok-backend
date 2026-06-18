package aurora.supply_wok.platform.inventory.application.internal.queryservices;

import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllInventoryActivitiesQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllStockMovementsByItemIdQuery;
import aurora.supply_wok.platform.inventory.domain.services.InventoryQueryService;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryActivityRepository;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final StockMovementRepository stockMovementRepository;
    private final InventoryActivityRepository inventoryActivityRepository;

    public InventoryQueryServiceImpl(StockMovementRepository stockMovementRepository, InventoryActivityRepository inventoryActivityRepository)
    {
        this.stockMovementRepository = stockMovementRepository;
        this.inventoryActivityRepository = inventoryActivityRepository;
    }

    @Override
    public List<StockMovement> handle(GetAllStockMovementsByItemIdQuery query) {
        return this.stockMovementRepository.findAllByItemIdOrderByDateDesc(query.itemId());
    }

    @Override
    public List<InventoryActivity> handle(GetAllInventoryActivitiesQuery query) {
        return this.inventoryActivityRepository.findAll();
    }
}
