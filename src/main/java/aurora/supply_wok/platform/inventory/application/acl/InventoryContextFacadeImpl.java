package aurora.supply_wok.platform.inventory.application.acl;

import aurora.supply_wok.platform.inventory.application.queryservices.SupplyQueryService;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetTotalSupplyStockQuery;
import aurora.supply_wok.platform.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Inventory ACL facade.
 */
@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {

    private final SupplyQueryService supplyQueryService;

    public InventoryContextFacadeImpl(SupplyQueryService supplyQueryService) {
        this.supplyQueryService = supplyQueryService;
    }

    @Override
    public long getTotalSupplyStock() {
        return supplyQueryService.handle(new GetTotalSupplyStockQuery());
    }
}
