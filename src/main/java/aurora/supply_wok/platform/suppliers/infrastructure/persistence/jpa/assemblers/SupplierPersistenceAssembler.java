package aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.assemblers;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Supplier;
import aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;

/**
 * Static assembler between supplier domain and persistence representations.
 */
public final class SupplierPersistenceAssembler {

    private SupplierPersistenceAssembler() {
    }

    public static Supplier toDomainFromPersistence(SupplierPersistenceEntity entity) {
        if (entity == null) return null;

        var supplier = new Supplier();
        supplier.setId(entity.getId());
        supplier.setName(entity.getName());
        return supplier;
    }

    public static SupplierPersistenceEntity toPersistenceFromDomain(Supplier supplier) {
        if (supplier == null) return null;

        var entity = new SupplierPersistenceEntity();
        if (supplier.getId() != null) {
            entity.setId(supplier.getId());
        }
        entity.setName(supplier.getName());
        return entity;
    }
}
