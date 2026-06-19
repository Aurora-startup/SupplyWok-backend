package aurora.supply_wok.platform.alerts.interfaces.rest.transform;

import aurora.supply_wok.platform.alerts.domain.model.commands.CreateAlertRestaurantFromInventoryCommand;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.CreateAlertRestaurantFromInventoryResource;

/**
 * Maps the inventory-alert creation resource to its command counterpart.
 */
public final class CreateAlertRestaurantFromInventoryCommandFromResourceAssembler {

    private CreateAlertRestaurantFromInventoryCommandFromResourceAssembler() {
    }

    public static CreateAlertRestaurantFromInventoryCommand toCommandFromResource(CreateAlertRestaurantFromInventoryResource resource) {
        return new CreateAlertRestaurantFromInventoryCommand(resource.sensorId());
    }
}
