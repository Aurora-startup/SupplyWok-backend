package aurora.supply_wok.platform.inventory.domain.model.entities;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_activities")
@Getter
public class InventoryActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ActivityType type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "activity_date", nullable = false)
    private LocalDateTime date;

    public InventoryActivity() {

    }

    public InventoryActivity(Long itemId, ActivityType type, String description, LocalDateTime date) {
        if (itemId == null || itemId < 0)
            throw new IllegalArgumentException("Item ID cannot be null or less than 0");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or empty");

        this.itemId = itemId;
        this.type = type;
        this.description = description;
        this.date = (date != null) ? date : LocalDateTime.now();
    }
}