package aurora.supply_wok.platform.inventory.interfaces.rest.transform;

import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.InventoryActivityResource;

public class InventoryActivityResourceFromEntityAssembler {

    public static InventoryActivityResource toResourceFromEntity(InventoryActivity entity) {
        return new InventoryActivityResource(
                entity.getId(),
                entity.getItemId(),
                entity.getType().name(),
                entity.getDescription(),
                entity.getDate()
        );
    }
}