package aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories;

import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryActivityPersistenceRepository extends JpaRepository<InventoryActivity, Long> {
    Page<InventoryActivity> findAllByOrderByDateDesc(Pageable pageable);
}
