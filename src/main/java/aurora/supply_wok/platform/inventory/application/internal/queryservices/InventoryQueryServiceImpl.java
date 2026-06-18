package aurora.supply_wok.platform.inventory.application.internal.queryservices;

import aurora.supply_wok.platform.inventory.application.queryservices.InventoryQueryService;
import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllInventoryActivitiesQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllStockMovementsByItemIdQuery;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryActivityPersistenceRepository;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.StockMovementPersistenceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final StockMovementPersistenceRepository stockMovementRepository;
    private final InventoryActivityPersistenceRepository inventoryActivityRepository;

    public InventoryQueryServiceImpl(StockMovementPersistenceRepository stockMovementRepository,
                                     InventoryActivityPersistenceRepository inventoryActivityRepository)
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
        var pageRequest = PageRequest.of(query.page(), query.size());
        return this.inventoryActivityRepository.findAllByOrderByDateDesc(pageRequest).getContent();
    }
}
