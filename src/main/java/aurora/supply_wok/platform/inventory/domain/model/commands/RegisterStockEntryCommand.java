package aurora.supply_wok.platform.inventory.domain.model.commands;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;

import java.time.LocalDateTime;

public record RegisterStockEntryCommand(SupplierId supplierId, double amount, LocalDateTime date, String reason) {

    public RegisterStockEntryCommand {
        if (supplierId == null) {
            throw new IllegalArgumentException("supplierId cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount cannot be negative or zero");
        }
        if (reason == null || reason.isEmpty()) {
            throw new IllegalArgumentException("Must provide reason");
        }
    }
}

