package aurora.supply_wok.platform.alerts.application.internal.outboundservices.acl;

import aurora.supply_wok.platform.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

/**
 * ACL service used by the Alerts bounded context to interact with Inventory capabilities.
 */
@Service
public class ExternalInventoryService {

    private final InventoryContextFacade inventoryContextFacade;

    public ExternalInventoryService(InventoryContextFacade inventoryContextFacade) {
        this.inventoryContextFacade = inventoryContextFacade;
    }

    public long getTotalSupplyStock() {
        return inventoryContextFacade.getTotalSupplyStock();
    }
}
