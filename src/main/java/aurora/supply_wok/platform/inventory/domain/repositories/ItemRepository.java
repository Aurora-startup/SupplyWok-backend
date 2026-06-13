package aurora.supply_wok.platform.inventory.domain.repositories;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.StockStatus;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(Long id);

    List<Item> findAll();

    List<Item> findAllByCategoryId(Long categoryId);

    List<Item> findAllByRestaurantId(RestaurantId restaurantId);

    List<Item> findAllByFilters(SupplierId supplierId, Long categoryId, StockStatus stockStatus);

    boolean existsByName(String name);

    Item save(Item item);

    void deleteById(Long itemId);

}
