package aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories;

import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementPersistenceRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findAllByItemIdOrderByDateDesc(Long itemId);

    void deleteByItemId(Long itemId);
}
