package aurora.supply_wok.platform.suppliers.domain.repositories;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Supplier;

import java.util.Optional;

/**
 * Supplier repository port.
 */
public interface SupplierRepository {
    Optional<Supplier> findById(Long id);

    boolean existsById(Long id);
}
