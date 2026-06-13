package aurora.supply_wok.platform.inventory.domain.model.valueobjects;

import java.time.LocalDateTime;

public record InventoryActivity(Long id, Long itemId, ActivityType type, String description, LocalDateTime date) {

    public InventoryActivity
    {
       if (id == null || id < 0)
           throw new IllegalArgumentException("InventoryActivity ID cannot be null or less than 0");
       if (itemId == null || itemId < 0)
           throw new IllegalArgumentException("Item ID cannot be null or less than 0");
       if (description == null || description.isBlank()){
           throw new IllegalArgumentException("Description cannot be null or empty");
       }
    }
}
