package aurora.supply_wok.platform.suppliers.interfaces.acl;

import aurora.supply_wok.platform.suppliers.interfaces.acl.resources.SupplierIdentityAclResource;

import java.util.Optional;

/**
 * ACL facade that exposes supplier identity data to other bounded contexts.
 */
public interface SuppliersContextFacade {
    Optional<SupplierIdentityAclResource> getSupplierIdentityById(Long supplierId);
}
