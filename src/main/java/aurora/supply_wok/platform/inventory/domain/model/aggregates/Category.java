package aurora.supply_wok.platform.inventory.domain.model.aggregates;

import aurora.supply_wok.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category extends AuditableAbstractAggregateRoot<Category> {

    private Long id;
    private String name;
    private String description;

    public Category(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.description = description;
    }

    public void updateInformation(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
