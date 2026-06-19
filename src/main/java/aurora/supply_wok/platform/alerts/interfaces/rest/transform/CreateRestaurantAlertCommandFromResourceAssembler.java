package aurora.supply_wok.platform.alerts.interfaces.rest.transform;

import aurora.supply_wok.platform.alerts.domain.model.commands.CreateRestaurantAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.valueobjects.EAlertSeverity;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.CreateRestaurantAlertResource;

import java.util.Arrays;

public class CreateRestaurantAlertCommandFromResourceAssembler {

    public static CreateRestaurantAlertCommand toCommandFromResource(CreateRestaurantAlertResource resource) {
        var severity = Arrays.stream(EAlertSeverity.values())
                .filter(value -> value.name().equalsIgnoreCase(resource.severity()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid alert severity: " + resource.severity()));

        return new CreateRestaurantAlertCommand(
                severity,
                resource.detail(),
                resource.sensorId()
        );
    }
}
