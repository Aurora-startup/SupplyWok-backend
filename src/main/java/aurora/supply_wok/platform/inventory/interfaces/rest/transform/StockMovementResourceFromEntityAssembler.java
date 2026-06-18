package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.StockMovementResource;

public class StockMovementResourceFromEntityAssembler {

    public static StockMovementResource toResourceFromEntity(StockMovement entity) {
        return new StockMovementResource(
                entity.getId(),
                entity.getItemId(),
                (entity.getSupplierId() != null) ? entity.getSupplierId().supplierId() : null,
                entity.getType().name(),
                entity.getAmount(),
                entity.getReason(),
                entity.getDate()
        );
    }
}