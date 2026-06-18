package aurora.supply_wok.platform.purchasing.infrastructure.persistence.jpa.adapters;

import aurora.supply_wok.platform.purchasing.domain.model.aggregates.PurchaseOrder;
import aurora.supply_wok.platform.purchasing.domain.repositories.PurchaseOrderRepository;
import aurora.supply_wok.platform.purchasing.infrastructure.persistence.jpa.assemblers.PurchaseOrderPersistenceAssembler;
import aurora.supply_wok.platform.purchasing.infrastructure.persistence.jpa.repositories.PurchaseOrderPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the purchase order domain repository port with Spring Data JPA.
 */
@Repository
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final PurchaseOrderPersistenceRepository purchaseOrderPersistenceRepository;

    public PurchaseOrderRepositoryImpl(PurchaseOrderPersistenceRepository purchaseOrderPersistenceRepository) {
        this.purchaseOrderPersistenceRepository = purchaseOrderPersistenceRepository;
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderPersistenceRepository.findAllByOrderByIdAsc()
                .stream()
                .map(PurchaseOrderPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<PurchaseOrder> findAllBySupplierId(Long supplierId) {
        return purchaseOrderPersistenceRepository.findAllBySupplierIdOrderByIdAsc(supplierId)
                .stream()
                .map(PurchaseOrderPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<PurchaseOrder> findById(Long purchaseOrderId) {
        return purchaseOrderPersistenceRepository.findById(purchaseOrderId)
                .map(PurchaseOrderPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByCode(String code) {
        return purchaseOrderPersistenceRepository.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, Long excludedId) {
        return purchaseOrderPersistenceRepository.existsByCodeAndIdNot(code, excludedId);
    }

    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        var saved = purchaseOrderPersistenceRepository.save(PurchaseOrderPersistenceAssembler.toPersistenceFromDomain(purchaseOrder));
        return PurchaseOrderPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public void delete(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getId() != null) {
            purchaseOrderPersistenceRepository.deleteById(purchaseOrder.getId());
        }
    }
}
