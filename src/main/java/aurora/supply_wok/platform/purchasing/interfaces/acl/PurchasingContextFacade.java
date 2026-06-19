package aurora.supply_wok.platform.purchasing.interfaces.acl;

import aurora.supply_wok.platform.purchasing.domain.model.commands.CreatePurchaseOrderItemCommand;
import aurora.supply_wok.platform.purchasing.interfaces.acl.resources.PurchaseOrderAclResource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ACL facade that exposes Purchasing bounded context capabilities to other bounded contexts.
 */
public interface PurchasingContextFacade {
    Optional<Long> createPurchaseOrder(String code,
                                       Long supplierId,
                                       String supplierName,
                                       String restaurantName,
                                       LocalDate orderDate,
                                       LocalDate estimatedDate,
                                       String priority,
                                       List<CreatePurchaseOrderItemCommand> items);

    List<PurchaseOrderAclResource> getPurchaseOrdersBySupplierId(Long supplierId);

    boolean updatePurchaseOrderStatus(Long purchaseOrderId, String status);

    BigDecimal calculateSupplierSla(Long supplierId);
}
