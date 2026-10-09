package aurora.supply_wok.platform.purchasing.domain.model.aggregates;

import aurora.supply_wok.platform.purchasing.domain.model.entities.PurchaseOrderItem;
import aurora.supply_wok.platform.purchasing.domain.model.valueobjects.EPurchaseOrderPriority;
import aurora.supply_wok.platform.purchasing.domain.model.valueobjects.EPurchaseOrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseOrderTests {

    private PurchaseOrder createOrderWithStatus(EPurchaseOrderStatus status) {
        return new PurchaseOrder(
                "PO-100",
                1L,
                "Supplier A",
                "Restaurant A",
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                EPurchaseOrderPriority.Medium,
                status,
                List.of(new PurchaseOrderItem(1L, "Rice", BigDecimal.valueOf(10), BigDecimal.valueOf(5), "Kg"))
        );
    }

    @Test
    void updateInformationSucceedsWhenStatusIsPending() {
        var order = createOrderWithStatus(EPurchaseOrderStatus.Pending);

        order.updateInformation(
                "PO-100-UPDATED",
                2L,
                "Supplier B",
                "Restaurant B",
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                EPurchaseOrderPriority.High,
                EPurchaseOrderStatus.Pending,
                List.of(new PurchaseOrderItem(2L, "Noodles", BigDecimal.valueOf(20), BigDecimal.valueOf(3), "Kg"))
        );

        assertThat(order.getCode()).isEqualTo("PO-100-UPDATED");
        assertThat(order.getSupplierId()).isEqualTo(2L);
        assertThat(order.getSupplierName()).isEqualTo("Supplier B");
        assertThat(order.getPriority()).isEqualTo(EPurchaseOrderPriority.High);
        assertThat(order.getItems()).hasSize(1);
    }

    @Test
    void updateInformationThrowsWhenStatusIsDelivered() {
        var order = createOrderWithStatus(EPurchaseOrderStatus.Delivered);

        assertThatThrownBy(() -> order.updateInformation(
                "PO-100-MODIFIED",
                1L,
                "Supplier A",
                "Restaurant A",
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                EPurchaseOrderPriority.High,
                EPurchaseOrderStatus.Delivered,
                List.of()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Only pending purchase orders can be modified.");
    }

    @Test
    void updateInformationThrowsWhenStatusIsInTransit() {
        var order = createOrderWithStatus(EPurchaseOrderStatus.InTransit);

        assertThatThrownBy(() -> order.updateInformation(
                "PO-100-MODIFIED",
                1L,
                "Supplier A",
                "Restaurant A",
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                EPurchaseOrderPriority.High,
                EPurchaseOrderStatus.InTransit,
                List.of()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Only pending purchase orders can be modified.");
    }
}
