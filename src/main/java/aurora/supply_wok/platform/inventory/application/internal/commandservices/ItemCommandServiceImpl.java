package aurora.supply_wok.platform.inventory.application.internal.commandservices;

import aurora.supply_wok.platform.inventory.application.commandservices.ItemCommandService;
import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.commands.CreateItemCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteItemCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockAdjustmentCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockEntryCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockExitCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.RegisterStockWriteOffCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateItemCommand;
import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.ActivityType;
import aurora.supply_wok.platform.inventory.domain.model.valueobjects.MovementType;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryActivityPersistenceRepository;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.ItemPersistenceRepository;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.StockMovementPersistenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemCommandServiceImpl implements ItemCommandService {

    private final ItemPersistenceRepository itemRepository;
    private final StockMovementPersistenceRepository stockMovementRepository;
    private final InventoryActivityPersistenceRepository inventoryActivityRepository;

    public ItemCommandServiceImpl(ItemPersistenceRepository itemRepository,
                                  StockMovementPersistenceRepository stockMovementRepository,
                                  InventoryActivityPersistenceRepository inventoryActivityRepository) {
        this.itemRepository = itemRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.inventoryActivityRepository = inventoryActivityRepository;
    }

    @Override
    public Long handle(CreateItemCommand command) {
         var name = command.name();
         if (this.itemRepository.existsByName(name)) {
             throw new IllegalArgumentException("Item with name " + name + " already exists");
         }

         var item = new Item(command.categoryId(), command.restaurantId(), command.supplierId(),
                 command.name(), command.brand(), command.unitOfMeasure(), command.imageUrl(), command.stock());

         try {
             this.itemRepository.save(item);
         } catch (Exception e){
             throw new IllegalArgumentException("Error while saving item: " + e.getMessage());
        }

         return item.getId();
    }

    @Override
    public Optional<Item> handle(UpdateItemCommand command) {

        var itemId = command.itemId();

        if(!this.itemRepository.existsById(itemId))
        {
            throw new IllegalArgumentException("Item with ID " + itemId + " does not exist");
        }

        var itemToUpdate = this.itemRepository.findById(itemId).get();
        itemToUpdate.updateInformation(command.categoryId(), command.supplierId(),
                command.name(), command.brand(), command.unitOfMeasure(), command.imageUrl());

        try {
            var updateItem = this.itemRepository.save(itemToUpdate);
            return Optional.of(updateItem);
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while updating item: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteItemCommand command) {

        if (!this.itemRepository.existsById(command.itemId())) {
            throw new IllegalArgumentException("Item with ID " + command.itemId() + " does not exist");
        }

        try {
            this.itemRepository.deleteById(command.itemId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting item: " + e.getMessage());
        }

    }

    @Transactional
    @Override
    public void handle(RegisterStockEntryCommand command) {

        var item = itemRepository.findById(command.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Item with ID " + command.itemId() + " does not exist"));

        item.registerEntry(command.amount(), command.supplierId(), command.reason(), command.date());
        itemRepository.save(item);

        var movement = new StockMovement(command.itemId(), command.supplierId(), MovementType.ENTRY, command.amount(), command.date(), command.reason());
        this.stockMovementRepository.save(movement);

        var description = "Stock entry of " + command.amount() + " " + item.getUnitOfMeasure() + " for item " + item.getName();
        var activity = new InventoryActivity(command.itemId(), ActivityType.STOCK_ENTRY, description, command.date());
        this.inventoryActivityRepository.save(activity);
    }

    @Transactional
    @Override
    public void handle(RegisterStockExitCommand command) {
        var item = itemRepository.findById(command.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Item with ID " + command.itemId() + " does not exist"));

        if (item.getStock().getCurrentStock() < command.amount()) {
            throw new IllegalArgumentException("Insufficient stock for item: " + item.getName());
        }

        item.registerExit(command.amount(), command.reason(), command.date(), MovementType.EXIT);
        itemRepository.save(item);

        var movement = new StockMovement(command.itemId(), null, MovementType.EXIT, command.amount(), command.date(), command.reason());
        this.stockMovementRepository.save(movement);

        var description = "Stock exit of " + command.amount() + " " + item.getUnitOfMeasure() + " for item " + item.getName();
        this.inventoryActivityRepository.save(new InventoryActivity(command.itemId(), ActivityType.STOCK_EXIT, description, command.date()));
    }

    @Transactional
    @Override
    public void handle(RegisterStockWriteOffCommand command) {
        var item = itemRepository.findById(command.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Item with ID " + command.itemId() + " does not exist"));

        if (item.getStock().getCurrentStock() < command.amount()) {
            throw new IllegalArgumentException("Insufficient stock to write-off item: " + item.getName());
        }

        item.registerExit(command.amount(), command.reason(), command.date(), MovementType.WRITE_OFF);
        itemRepository.save(item);

        var movement = new StockMovement(command.itemId(), null, MovementType.WRITE_OFF, command.amount(), command.date(), command.reason());
        this.stockMovementRepository.save(movement);

        var description = "Stock write-off of " + command.amount() + " for item " + item.getName();
        this.inventoryActivityRepository.save(new InventoryActivity(command.itemId(), ActivityType.STOCK_WRITE_OFF, description, command.date()));
    }

    @Transactional
    @Override
    public void handle(RegisterStockAdjustmentCommand command) {
        var item = itemRepository.findById(command.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Item with ID " + command.itemId() + " does not exist"));

        var previousStock = item.getStock().getCurrentStock();
        item.registerAdjustment(command.amount(), command.reason(), command.date());
        itemRepository.save(item);

        var movement = new StockMovement(command.itemId(), null, MovementType.ADJUSTMENT, command.amount(), command.date(), command.reason());
        this.stockMovementRepository.save(movement);

        var description = "Stock adjustment from " + previousStock + " to " + command.amount() + " for item " + item.getName();
        this.inventoryActivityRepository.save(new InventoryActivity(command.itemId(), ActivityType.STOCK_ADJUSTMENT, description, command.date()));
    }
}
