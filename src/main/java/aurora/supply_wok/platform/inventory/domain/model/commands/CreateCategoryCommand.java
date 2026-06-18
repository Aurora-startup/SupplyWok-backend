package aurora.supply_wok.platform.inventory.domain.model.commands;

public record CreateCategoryCommand(String name, String description) {

    public CreateCategoryCommand {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Category description cannot be null or empty");
        }
    }
}
