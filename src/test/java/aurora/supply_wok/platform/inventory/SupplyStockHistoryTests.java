package aurora.supply_wok.platform.inventory;

import aurora.supply_wok.platform.inventory.application.commandservices.StockMovementCommandService;
import aurora.supply_wok.platform.inventory.application.commandservices.SupplyCommandService;
import aurora.supply_wok.platform.inventory.domain.model.commands.CreateStockMovementCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.CreateSupplyCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateSupplyCommand;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.EMovementType;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.EUnitOfMeasure;
import aurora.supply_wok.platform.inventory.domain.repositories.SupplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SupplyStockHistoryTests {

    @Autowired
    private SupplyCommandService supplyCommandService;

    @Autowired
    private StockMovementCommandService stockMovementCommandService;

    @Autowired
    private SupplyRepository supplyRepository;

    @Test
    void updatingSupplyPreservesStockMovementHistory() {
        var supply = supplyCommandService.handle(new CreateSupplyCommand(
                "Fresh Basil",
                EUnitOfMeasure.Kilograms,
                20,
                5,
                "Herbs"
        ));

        var movement = stockMovementCommandService.handle(new CreateStockMovementCommand(
                supply.getId(),
                EMovementType.Entry,
                10,
                LocalDateTime.now(),
                "Initial batch arrival"
        ));
        assertThat(movement).isPresent();

        var movementsBeforeUpdate = supplyRepository.findStockMovementsBySupplyId(supply.getId());
        assertThat(movementsBeforeUpdate).hasSize(1);

        supplyCommandService.handle(new UpdateSupplyCommand(
                supply.getId(),
                "Fresh Organic Basil",
                supply.getUnitOfMeasure(),
                30,
                5,
                supply.getCategory()
        ));

        var movementsAfterUpdate = supplyRepository.findStockMovementsBySupplyId(supply.getId());
        assertThat(movementsAfterUpdate)
                .as("Stock movements must be preserved after updating supply")
                .hasSize(1);
        assertThat(movementsAfterUpdate.get(0).getReason()).isEqualTo("Initial batch arrival");
    }
}
