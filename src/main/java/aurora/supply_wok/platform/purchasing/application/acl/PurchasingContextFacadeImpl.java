package aurora.supply_wok.platform.purchasing.application.acl;

import aurora.supply_wok.platform.purchasing.application.commandservices.PurchaseOrderCommandService;
import aurora.supply_wok.platform.purchasing.application.queryservices.PurchaseOrderQueryService;
import aurora.supply_wok.platform.purchasing.domain.model.commands.CreatePurchaseOrderCommand;
import aurora.supply_wok.platform.purchasing.domain.model.commands.CreatePurchaseOrderItemCommand;
import aurora.supply_wok.platform.purchasing.domain.model.commands.UpdatePurchaseOrderStatusCommand;
import aurora.supply_wok.platform.purchasing.domain.model.queries.GetPurchaseOrdersBySupplierIdQuery;
import aurora.supply_wok.platform.purchasing.domain.model.valueobjects.PurchaseOrderSla;
import aurora.supply_wok.platform.purchasing.interfaces.acl.PurchasingContextFacade;
import aurora.supply_wok.platform.purchasing.interfaces.acl.resources.PurchaseOrderAclResource;
import aurora.supply_wok.platform.purchasing.interfaces.acl.resources.PurchaseOrderItemAclResource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Application-layer implementation of the Purchasing ACL facade.
 */
@Service
public class PurchasingContextFacadeImpl implements PurchasingContextFacade {

    private final PurchaseOrderCommandService purchaseOrderCommandService;
    private final PurchaseOrderQueryService purchaseOrderQueryService;

    public PurchasingContextFacadeImpl(PurchaseOrderCommandService purchaseOrderCommandService,
                                       PurchaseOrderQueryService purchaseOrderQueryService) {
        this.purchaseOrderCommandService = purchaseOrderCommandService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
    }

    @Override
    public Optional<Long> createPurchaseOrder(String code,
                                              Long supplierId,
                                              String supplierName,
                                              String restaurantName,
                                              LocalDate orderDate,
                                              LocalDate estimatedDate,
                                              String priority,
                                              List<CreatePurchaseOrderItemCommand> items) {
        var command = new CreatePurchaseOrderCommand(
                code,
                supplierId,
                supplierName,
                restaurantName,
                orderDate,
                estimatedDate,
                priority,
                "Pending",
                items
        );
        return purchaseOrderCommandService.handle(command).map(order -> order.getId());
    }

    @Override
    public List<PurchaseOrderAclResource> getPurchaseOrdersBySupplierId(Long supplierId) {
        return purchaseOrderQueryService.handle(new GetPurchaseOrdersBySupplierIdQuery(supplierId))
                .stream()
                .map(order -> new PurchaseOrderAclResource(
                        order.getId(),
                        order.getCode(),
                        order.getSupplierId(),
                        order.getSupplierName(),
                        order.getRestaurantName(),
                        order.getOrderDate() == null ? null : order.getOrderDate().toString(),
                        order.getEstimatedDate() == null ? "" : order.getEstimatedDate().toString(),
                        order.getPriority().name(),
                        toStatusLabel(order.getStatus().name()),
                        order.getItems().stream()
                                .map(item -> new PurchaseOrderItemAclResource(
                                        item.getId(),
                                        item.getInventoryItemId(),
                                        item.getProductName(),
                                        item.getQuantity(),
                                        item.getUnitPrice(),
                                        item.getUnitType()
                                ))
                                .toList()
                ))
                .toList();
    }

    @Override
    public boolean updatePurchaseOrderStatus(Long purchaseOrderId, String status) {
        var command = new UpdatePurchaseOrderStatusCommand(purchaseOrderId, status);
        return purchaseOrderCommandService.handle(command).isPresent();
    }

    @Override
    public BigDecimal calculateSupplierSla(Long supplierId) {
        var orders = purchaseOrderQueryService.handle(new GetPurchaseOrdersBySupplierIdQuery(supplierId));
        return new PurchaseOrderSla(supplierId, orders).getComplianceRate();
    }

    private String toStatusLabel(String statusName) {
        return "InTransit".equals(statusName) ? "In Transit" : statusName;
    }
}
