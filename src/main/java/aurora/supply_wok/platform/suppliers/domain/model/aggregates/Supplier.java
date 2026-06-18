package aurora.supply_wok.platform.suppliers.domain.model.aggregates;

import aurora.supply_wok.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

/**
 * Supplier aggregate root.
 */
@Getter
public class Supplier extends AbstractDomainAggregateRoot<Supplier> {

    @Setter
    private Long id;

    @Setter
    private String name;

    public Supplier() {
        this.name = "";
    }

    public Supplier(String name) {
        this.name = name;
    }
}
