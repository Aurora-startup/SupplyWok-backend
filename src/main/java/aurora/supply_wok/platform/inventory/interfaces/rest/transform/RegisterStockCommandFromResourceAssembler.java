package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.commands.*;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.RegisterStockResource;

public class RegisterStockCommandFromResourceAssembler {
    public static Object toCommandFromResource(RegisterStockResource resource) {
        return switch (resource.type().toUpperCase()) {
            case "ENTRY" -> new RegisterStockEntryCommand(
                    resource.itemId(),
                    new SupplierId(resource.supplierId()),
                    resource.quantity(),
                    null,
                    resource.reason()
            );

            case "EXIT" -> new RegisterStockExitCommand(
                    resource.itemId(),
                    null,
                    resource.quantity(),
                    null,
                    resource.reason()
            );

            case "WRITE_OFF" -> new RegisterStockWriteOffCommand(
                    resource.itemId(),
                    null,
                    resource.quantity(),
                    null,
                    resource.reason()
            );

            case "ADJUSTMENT" -> new RegisterStockAdjustmentCommand(
                    resource.itemId(),
                    null,
                    resource.quantity(),
                    null,
                    resource.reason()
            );

            default -> throw new IllegalArgumentException("Unknown movement type: " + resource.type());
        };
    }
}