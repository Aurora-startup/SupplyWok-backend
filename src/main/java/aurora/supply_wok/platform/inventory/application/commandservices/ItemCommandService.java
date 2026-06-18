package aurora.supply_wok.platform.inventory.application.commandservices;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.commands.CreateItemCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteItemCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockAdjustmentCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockEntryCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockExitCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockWriteOffCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateItemCommand;

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
