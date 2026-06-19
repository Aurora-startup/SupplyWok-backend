package aurora.supply_wok.platform.inventory.domain.services;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.commands.*;

import java.util.Optional;

public interface ItemCommandService {
    Long handle(CreateItemCommand command);
    Optional<Item> handle(UpdateItemCommand command);
    void handle(DeleteItemCommand command);

    void handle(RegisterStockEntryCommand command);
    void handle(RegisterStockExitCommand command);
    void handle(RegisterStockAdjustmentCommand command);
    void handle(RegisterStockWriteOffCommand command);
}