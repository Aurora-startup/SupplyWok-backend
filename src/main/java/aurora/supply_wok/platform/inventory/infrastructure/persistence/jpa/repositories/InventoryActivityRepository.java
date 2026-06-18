package aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories;

import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryActivityRepository extends JpaRepository<InventoryActivity, Long> {

    List<InventoryActivity> findAllByOrderByDateDesc();
}