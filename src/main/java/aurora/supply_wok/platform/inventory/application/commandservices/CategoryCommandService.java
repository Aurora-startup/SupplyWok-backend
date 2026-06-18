package aurora.supply_wok.platform.inventory.application.commandservices;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Category;
import aurora.supply_wok.platform.inventory.domain.model.commands.CreateCategoryCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteCategoryCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateCategoryCommand;

import java.util.Optional;

public interface CategoryCommandService {
    Long handle(CreateCategoryCommand command);
    Optional<Category> handle(UpdateCategoryCommand command);
    void handle(DeleteCategoryCommand command);
}
