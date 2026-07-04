package aurora.supply_wok.platform.suppliers.application.internal.commandservices;

import aurora.supply_wok.platform.suppliers.application.commandservices.SupplierCommandService;
import aurora.supply_wok.platform.suppliers.domain.model.aggregates.Supplier;
import aurora.supply_wok.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import aurora.supply_wok.platform.suppliers.domain.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Default supplier command service implementation.
 */
@Service
public class SupplierCommandServiceImpl implements SupplierCommandService {

    private final SupplierRepository supplierRepository;

    public SupplierCommandServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier handle(CreateSupplierCommand command) {
        var supplier = new Supplier(
                UUID.randomUUID(),
                command.name(),
                command.contactName(),
                command.email(),
                command.phone(),
                command.category(),
                command.linkedDate(),
                command.sla(),
                command.responseTime()
        );
        return supplierRepository.save(supplier);
    }
}
