package aurora.supply_wok.platform.inventory.domain.model.aggregates;

import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.UnitOfMeasure;
import aurora.supply_wok.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Item extends AuditableAbstractAggregateRoot<Item> {

    @Setter
    private Long id;

    @Setter
    private RestaurantId restaurantId;

    @Setter
    private String name;

    @Setter
    private String brand;

    @Setter
    private UnitOfMeasure unitOfMeasure;

    @Setter
    private String imageUrl;

}
