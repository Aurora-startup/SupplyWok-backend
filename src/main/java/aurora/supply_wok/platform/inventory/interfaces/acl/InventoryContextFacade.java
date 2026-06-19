package aurora.supply_wok.platform.inventory.interfaces.acl;

/**
 * ACL facade that exposes inventory stock totals to other bounded contexts.
 */
public interface InventoryContextFacade {
    long getTotalSupplyStock();
}
