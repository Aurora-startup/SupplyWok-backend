package aurora.supply_wok.platform.inventory.domain.model.commands;

public record DeleteCategoryCommand(Long categoryId) {

    public DeleteCategoryCommand {
        if (categoryId == null || categoryId < 0) {
            throw new IllegalArgumentException("categoryId cannot be null or less than 0");
        }
    }
}
