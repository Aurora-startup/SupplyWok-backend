package aurora.supply_wok.platform.suppliers.domain.repositories;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * Supplier repository port.
 */
public interface SupplierRepository {
    List<Supplier> findAll();

    Optional<Supplier> findById(Long id);

    boolean existsById(Long id);
}
