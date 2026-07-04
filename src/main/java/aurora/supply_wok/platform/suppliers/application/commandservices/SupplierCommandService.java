package aurora.supply_wok.platform.suppliers.application.commandservices;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Supplier;
import aurora.supply_wok.platform.suppliers.domain.model.commands.CreateSupplierCommand;

/**
 * Application contract for supplier write use cases.
 */
public interface SupplierCommandService {

    Supplier handle(CreateSupplierCommand command);
}
