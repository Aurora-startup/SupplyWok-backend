package aurora.supply_wok.platform.suppliers.application.acl;

import aurora.supply_wok.platform.suppliers.application.queryservices.SupplierQueryService;
import aurora.supply_wok.platform.suppliers.domain.model.queries.GetAllSuppliersQuery;
import aurora.supply_wok.platform.suppliers.interfaces.acl.SuppliersContextFacade;
import aurora.supply_wok.platform.suppliers.interfaces.acl.resources.SupplierIdentityAclResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application-layer implementation of the Suppliers ACL facade.
 */
@Service
public class SuppliersContextFacadeImpl implements SuppliersContextFacade {

    private final SupplierQueryService supplierQueryService;

    public SuppliersContextFacadeImpl(SupplierQueryService supplierQueryService) {
        this.supplierQueryService = supplierQueryService;
    }

    @Override
    public Optional<SupplierIdentityAclResource> getSupplierIdentityById(Long supplierId) {
        if (supplierId == null || supplierId <= 0) {
            return Optional.empty();
        }

        return supplierQueryService.handle(new GetAllSuppliersQuery()).stream()
                .filter(supplier -> supplierId.equals(supplier.getId()))
                .findFirst()
                .map(supplier -> new SupplierIdentityAclResource(supplier.getId(), supplier.getName()));
    }
}
