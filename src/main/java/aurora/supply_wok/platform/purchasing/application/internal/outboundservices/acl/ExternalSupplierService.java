package aurora.supply_wok.platform.purchasing.application.internal.outboundservices.acl;

import aurora.supply_wok.platform.suppliers.interfaces.acl.SuppliersContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ACL service used by the Purchasing bounded context to interact with Suppliers capabilities.
 */
@Service
public class ExternalSupplierService {

    private final SuppliersContextFacade suppliersContextFacade;

    public ExternalSupplierService(SuppliersContextFacade suppliersContextFacade) {
        this.suppliersContextFacade = suppliersContextFacade;
    }

    public Optional<String> fetchSupplierNameById(Long supplierId) {
        return suppliersContextFacade.getSupplierIdentityById(supplierId)
                .map(supplierIdentity -> supplierIdentity.name())
                .filter(name -> !name.isBlank());
    }
}
