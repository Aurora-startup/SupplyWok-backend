package aurora.supply_wok.platform.purchasing.interfaces.rest.transform;

import aurora.supply_wok.platform.purchasing.interfaces.rest.resources.CreatePurchaseOrderResource;
import aurora.supply_wok.platform.purchasing.interfaces.rest.resources.PurchaseOrderItemResource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreatePurchaseOrderCommandFromResourceAssemblerTests {

    @Test
    void ignoresClientSideLineItemIdsWhenCreatingPurchaseOrder() {
        var resource = new CreatePurchaseOrderResource(
                "PO-TEST-1",
                1L,
                "Golden Wok Produce",
                "Gran Dragon Chifa",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                "High",
                "Pending",
                List.of(new PurchaseOrderItemResource(
                        1783513738209L,
                        null,
                        "arroz",
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(2.5),
                        "Kg"
                ))
        );

        var command = CreatePurchaseOrderCommandFromResourceAssembler.toCommandFromResource(resource);

        assertThat(command.items()).hasSize(1);
        assertThat(command.items().getFirst().id()).isNull();
    }
}
