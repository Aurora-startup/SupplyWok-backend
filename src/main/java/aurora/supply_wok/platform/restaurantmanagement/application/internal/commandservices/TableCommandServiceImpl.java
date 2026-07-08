package aurora.supply_wok.platform.restaurantmanagement.application.internal.commandservices;

import aurora.supply_wok.platform.restaurantmanagement.domain.model.aggregates.RestaurantTable;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.commands.CreateTableCommand;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.commands.DeleteTableCommand;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.commands.UpdateTableCommand;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.commands.UpdateTableStatusCommand;
import aurora.supply_wok.platform.restaurantmanagement.domain.model.services.TableCommandService;
import aurora.supply_wok.platform.restaurantmanagement.infrastructure.persistence.jpa.repositories.RestaurantTableRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TableCommandServiceImpl implements TableCommandService {

    private final RestaurantTableRepository tableRepository;

    public TableCommandServiceImpl(RestaurantTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public Long handle(CreateTableCommand command) {
        var table = new RestaurantTable(command);
        tableRepository.save(table);
        return table.getId();
    }

    @Override
    public Optional<RestaurantTable> handle(UpdateTableCommand command) {
        var result = tableRepository.findById(command.tableId());
        if (result.isEmpty())
            throw new IllegalArgumentException("Table with id %d not found".formatted(command.tableId()));
        var table = result.get();
        table.update(command);
        tableRepository.save(table);
        return Optional.of(table);
    }

    @Override
    public Optional<RestaurantTable> handle(UpdateTableStatusCommand command) {
        var result = tableRepository.findById(command.tableId());
        if (result.isEmpty())
            throw new IllegalArgumentException("Table with id %d not found".formatted(command.tableId()));
        var table = result.get();
        table.updateStatus(command);
        tableRepository.save(table);
        return Optional.of(table);
    }

    @Override
    public void handle(DeleteTableCommand command) {
        if (!tableRepository.existsById(command.tableId()))
            throw new IllegalArgumentException("Table with id %d not found".formatted(command.tableId()));
        tableRepository.deleteById(command.tableId());
    }
}
