package aurora.supply_wok.platform.suppliers.domain.repositories;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Client;

import java.util.List;

/**
 * Client repository port.
 */
public interface ClientRepository {
    List<Client> findAllBySupplierId(Long supplierId);
}
