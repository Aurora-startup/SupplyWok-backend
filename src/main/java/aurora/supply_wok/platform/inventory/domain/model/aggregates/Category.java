package aurora.supply_wok.platform.inventory.domain.model.aggregates;

import aurora.supply_wok.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "categories")
public class Category extends AuditableAbstractAggregateRoot<Category> {

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Getter
    @Column(name = "description", length = 255, nullable = true)
    private String description;

    public Category(String name, String description) {

        this.name = name;
        this.description = description;
    }

    public Category() {

    }

    public void updateInformation(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
