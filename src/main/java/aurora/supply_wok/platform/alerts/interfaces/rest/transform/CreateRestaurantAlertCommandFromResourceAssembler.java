package aurora.supply_wok.platform.alerts.interfaces.rest.transform;

import aurora.supply_wok.platform.alerts.domain.model.commands.CreateRestaurantAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.valueobjects.EAlertSeverity;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.CreateRestaurantAlertResource;

public class CreateRestaurantAlertCommandFromResourceAssembler {

    public static CreateRestaurantAlertCommand toCommandFromResource(CreateRestaurantAlertResource resource) {
        EAlertSeverity severity;
        try {
            severity = EAlertSeverity.valueOf(resource.severity().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid alert severity: " + resource.severity());
        }

        return new CreateRestaurantAlertCommand(
                severity,
                resource.detail(),
                resource.sensorId(),
                resource.sensorName()
        );
    }
}
