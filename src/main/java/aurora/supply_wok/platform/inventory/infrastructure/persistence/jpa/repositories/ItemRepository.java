package aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.RestaurantId;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.StockStatus;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.SupplierId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByCategoryId(Long categoryId);

    List<Item> findAllByRestaurantId(RestaurantId restaurantId);

    @Query("SELECT i FROM Item i WHERE " +
            "(:name IS NULL OR i.name LIKE %:name%) AND " +
            "(:supplierId IS NULL OR i.supplierId = :supplierId) AND " +
            "(:categoryId IS NULL OR i.categoryId = :categoryId) AND " +
            "(:stockStatus IS NULL OR i.stock.stockStatus = :stockStatus)")
    List<Item> findAllByFilters(String name, SupplierId supplierId, Long categoryId, StockStatus stockStatus);

    boolean existsByName(String name);
}