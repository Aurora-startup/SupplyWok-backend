package aurora.supply_wok.platform.suppliers.interfaces.acl;

import aurora.supply_wok.platform.suppliers.domain.repositories.SupplierRepository;
import aurora.supply_wok.platform.suppliers.interfaces.acl.resources.SupplierIdentityAclResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ACL facade that exposes supplier identity data to other bounded contexts.
 */
@Service
public class SuppliersContextFacade {

    private final SupplierRepository supplierRepository;

    public SuppliersContextFacade(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Optional<SupplierIdentityAclResource> getSupplierIdentityById(Long supplierId) {
        if (supplierId == null || supplierId <= 0) {
            return Optional.empty();
        }

        return supplierRepository.findById(supplierId)
                .map(supplier -> new SupplierIdentityAclResource(supplier.getId(), supplier.getName()));
    }
}
