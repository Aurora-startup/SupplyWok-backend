package aurora.supply_wok.platform.inventory;

import aurora.supply_wok.platform.alerts.application.queryservices.AlertQueryService;
import aurora.supply_wok.platform.alerts.domain.model.queries.GetAllRestaurantAlertsQuery;
import aurora.supply_wok.platform.inventory.application.commandservices.SupplyCommandService;
import aurora.supply_wok.platform.inventory.domain.model.commands.CreateSupplyCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateSupplyCommand;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.EUnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InventoryLowStockEventsTests {

    private final SupplyCommandService supplyCommandService;
    private final AlertQueryService alertQueryService;

    @Autowired
    InventoryLowStockEventsTests(SupplyCommandService supplyCommandService,
                                 AlertQueryService alertQueryService) {
        this.supplyCommandService = supplyCommandService;
        this.alertQueryService = alertQueryService;
    }

    @Test
    void createsRestaurantAlertWhenCreatedSupplyStartsAtMinimumStock() {
        supplyCommandService.handle(new CreateSupplyCommand(
                "Test low stock rice",
                EUnitOfMeasure.Kilograms,
                2,
                5,
                "Grains"
        ));

        var alerts = alertQueryService.handle(new GetAllRestaurantAlertsQuery());

        assertThat(alerts)
                .anySatisfy(alert -> {
                    assertThat(alert.getDetail())
                            .contains("Test low stock rice")
                            .contains("2/5");
                    assertThat(alert.getSensorId()).isZero();
                    assertThat(alert.getSensorName()).isEqualTo("System");
                });
    }

    @Test
    void createsRestaurantAlertWhenSupplyUpdateReachesMinimumStock() {
        var supply = supplyCommandService.handle(new CreateSupplyCommand(
                "Test updated low stock noodles",
                EUnitOfMeasure.Units,
                30,
                5,
                "Dry goods"
        ));

        supplyCommandService.handle(new UpdateSupplyCommand(
                supply.getId(),
                supply.getName(),
                supply.getUnitOfMeasure(),
                3,
                5,
                supply.getCategory()
        ));

        var alerts = alertQueryService.handle(new GetAllRestaurantAlertsQuery());

        assertThat(alerts)
                .anySatisfy(alert -> {
                    assertThat(alert.getDetail())
                            .contains("Test updated low stock noodles")
                            .contains("3/5");
                    assertThat(alert.getSensorId()).isZero();
                    assertThat(alert.getSensorName()).isEqualTo("System");
                });
    }
}
