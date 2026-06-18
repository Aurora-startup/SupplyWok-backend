package aurora.supply_wok.platform.inventory.application.internal.outboundservices.acl;

import aurora.supply_wok.platform.inventory.domain.services.ItemQueryService;

public class ItemsContextFacade {

    private final ItemQueryService itemQueryService;

    public ItemsContextFacade(ItemQueryService itemQueryService) {
        this.itemQueryService = itemQueryService;
    }
}
