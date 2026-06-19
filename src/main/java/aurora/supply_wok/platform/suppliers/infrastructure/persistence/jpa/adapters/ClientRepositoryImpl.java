package aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.adapters;

import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Client;
import aurora.supply_wok.platform.suppliers.domain.repositories.ClientRepository;
import aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.assemblers.ClientPersistenceAssembler;
import aurora.supply_wok.platform.suppliers.infrastructure.persistence.jpa.repositories.ClientPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository adapter that bridges the client domain repository port with Spring Data JPA.
 */
@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientPersistenceRepository clientPersistenceRepository;

    public ClientRepositoryImpl(ClientPersistenceRepository clientPersistenceRepository) {
        this.clientPersistenceRepository = clientPersistenceRepository;
    }

    @Override
    public List<Client> findAllBySupplierId(Long supplierId) {
        return clientPersistenceRepository.findAllBySupplierId(supplierId)
                .stream()
                .map(ClientPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
