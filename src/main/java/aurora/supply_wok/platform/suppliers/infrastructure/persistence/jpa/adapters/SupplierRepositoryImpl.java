package aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.adapters;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Supplier;
import aurora.supply_wok.platform.suppliers.domain.repositories.SupplierRepository;
import aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.assemblers.SupplierPersistenceAssembler;
import aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.repositories.SupplierPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository adapter that bridges the supplier domain repository port with Spring Data JPA.
 */
@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    private final SupplierPersistenceRepository supplierPersistenceRepository;

    public SupplierRepositoryImpl(SupplierPersistenceRepository supplierPersistenceRepository) {
        this.supplierPersistenceRepository = supplierPersistenceRepository;
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierPersistenceRepository.findById(id).map(SupplierPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsById(Long id) {
        return supplierPersistenceRepository.existsById(id);
    }
}
