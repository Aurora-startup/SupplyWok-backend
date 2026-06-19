package aurora.supply_wok.platform.alerts.interfaces.rest.transform;

import aurora.supply_wok.platform.alerts.domain.model.commands.CreateSupplierAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.valueobjects.EAlertSeverity;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.CreateSupplierAlertResource;

public class CreateSupplierAlertCommandFromResourceAssembler {

    public static CreateSupplierAlertCommand toCommandFromResource(CreateSupplierAlertResource resource) {
        EAlertSeverity severity;
        try {
            severity = EAlertSeverity.valueOf(resource.severity().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid alert severity: " + resource.severity());
        }

        return new CreateSupplierAlertCommand(severity, resource.detail());
    }
}
