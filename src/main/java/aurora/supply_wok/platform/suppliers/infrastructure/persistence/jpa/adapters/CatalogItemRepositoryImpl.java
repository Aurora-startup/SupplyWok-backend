package aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.adapters;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.CatalogItem;
import aurora.supply_wok.platform.suppliers.domain.repositories.CatalogItemRepository;
import aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.assemblers.CatalogItemPersistenceAssembler;
import aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.repositories.CatalogItemPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the catalog item domain repository port with Spring Data JPA.
 */
@Repository
public class CatalogItemRepositoryImpl implements CatalogItemRepository {

    private final CatalogItemPersistenceRepository catalogItemPersistenceRepository;

    public CatalogItemRepositoryImpl(CatalogItemPersistenceRepository catalogItemPersistenceRepository) {
        this.catalogItemPersistenceRepository = catalogItemPersistenceRepository;
    }

    @Override
    public List<CatalogItem> findAllBySupplierId(Long supplierId) {
        return catalogItemPersistenceRepository.findAllBySupplier_IdOrderByIdAsc(supplierId)
                .stream()
                .map(CatalogItemPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<CatalogItem> findByIdAndSupplierId(Long catalogItemId, Long supplierId) {
        return catalogItemPersistenceRepository.findByIdAndSupplier_Id(catalogItemId, supplierId)
                .map(CatalogItemPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public CatalogItem save(CatalogItem catalogItem) {
        var saved = catalogItemPersistenceRepository.save(CatalogItemPersistenceAssembler.toPersistenceFromDomain(catalogItem));
        return CatalogItemPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public void delete(CatalogItem catalogItem) {
        catalogItemPersistenceRepository.delete(CatalogItemPersistenceAssembler.toPersistenceFromDomain(catalogItem));
    }
}
