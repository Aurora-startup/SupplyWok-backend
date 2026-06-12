package aurora.supply_wok.platform.restaurantmanagement.domain.model.aggregates;

import aurora.supply_wok.platform.restaurantmanagement.domain.model.commands.AddComandaItemCommand;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.commands.CreateComandaCommand;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.commands.UpdateComandaStatusCommand;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.entities.ComandaItem;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.valueobjects.EComandaStatus;
import aurora.supply_wok.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comandas")
public class Comanda extends AuditableAbstractAggregateRoot<Comanda> {

    @Getter
    @NotNull
    @Column(name = "table_id", nullable = false)
    private Long tableId;

    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EComandaStatus status;

    @Getter
    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ComandaItem> items;

    public Comanda() {
        this.items = new ArrayList<>();
    }

    public Comanda(Long tableId) {
        this.tableId = tableId;
        this.status = EComandaStatus.OPEN;
        this.items = new ArrayList<>();
    }

    public Comanda(CreateComandaCommand command) {
        this.tableId = command.tableId();
        this.status = EComandaStatus.OPEN;
        this.items = new ArrayList<>();
    }

    public Comanda updateStatus(UpdateComandaStatusCommand command) {
        this.status = command.status();
        return this;
    }

    public ComandaItem addItem(AddComandaItemCommand command) {
        var item = new ComandaItem(command.productName(), command.quantity(), command.notes(), this);
        this.items.add(item);
        return item;
    }

    public void sendToKitchen() {
        this.status = EComandaStatus.SENT_TO_KITCHEN;
    }

    public void close() {
        this.status = EComandaStatus.CLOSED;
    }
}
