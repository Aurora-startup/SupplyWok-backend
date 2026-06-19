package aurora.supply_wok.platform.alerts.application.commandservices;

import aurora.supply_wok.platform.alerts.domain.model.commands.CreateRestaurantAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.commands.CreateSupplierAlertCommand;

public interface AlertCommandService {
    Long handle(CreateSupplierAlertCommand command);
    Long handle(CreateRestaurantAlertCommand command);
}
