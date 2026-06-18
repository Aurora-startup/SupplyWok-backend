package aurora.supply_wok.platform.inventory.domain.model.valueobjects;

public record RestaurantId(Long restaurantId) {

    public RestaurantId
    {
        if(restaurantId == null || restaurantId < 0)
        {
            throw new IllegalArgumentException("RestaurantId cannot be null or less than 0");
        }
    }

    public RestaurantId() {
        this(0L);
    }
}
