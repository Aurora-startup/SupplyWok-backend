package aurora.supply_wok.platform.inventory.domain.model.entities;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.MovementType;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "item_id", nullable = false)
    private Long itemId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "supplierId", column = @Column(name = "supplier_id"))
    })
    private SupplierId supplierId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String reason;

    public StockMovement() {

    }

    public StockMovement(Long itemId, SupplierId supplierId, MovementType type, double amount, LocalDateTime date, String reason) {
        if (itemId == null) throw new IllegalArgumentException("Item cannot be null");
        if (type == null) throw new IllegalArgumentException("Movement type cannot be null");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason cannot be empty");

        this.itemId = itemId;
        this.supplierId = supplierId;
        this.type = type;
        this.amount = amount;
        this.date = (date != null) ? date : LocalDateTime.now();
        this.reason = reason;
    }
}