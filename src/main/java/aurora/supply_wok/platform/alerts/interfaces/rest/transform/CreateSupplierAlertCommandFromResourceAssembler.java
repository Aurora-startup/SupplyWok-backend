package aurora.supply_wok.platform.alerts.interfaces.rest.transform;

import aurora.supply_wok.platform.alerts.domain.model.commands.CreateSupplierAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.valueobjects.EAlertSeverity;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.CreateSupplierAlertResource;

import java.util.Arrays;

public class CreateSupplierAlertCommandFromResourceAssembler {

    public static CreateSupplierAlertCommand toCommandFromResource(CreateSupplierAlertResource resource) {
        var severity = Arrays.stream(EAlertSeverity.values())
                .filter(value -> value.name().equalsIgnoreCase(resource.severity()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid alert severity: " + resource.severity()));

        return new CreateSupplierAlertCommand(severity, resource.detail());
    }
}
