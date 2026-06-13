package aurora.supply_wok.platform.inventory.domain.model.commands;

public record UpdateCategoryCommand(Long categoryId, String name, String description) {

    public UpdateCategoryCommand {
        if (categoryId == null || categoryId < 0) {
            throw new IllegalArgumentException("categoryId cannot be null or less than 0");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description cannot be null or empty");
        }
    }
}
