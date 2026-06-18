package aurora.supply_wok.platform.inventory.interfaces.rest;

import aurora.supply_wok.platform.inventory.domain.model.commands.*;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllItemsQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetItemByIdQuery;
import aurora.supply_wok.platform.inventory.domain.services.ItemCommandService;
import aurora.supply_wok.platform.inventory.domain.services.ItemQueryService;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.CreateItemResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.ItemResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.RegisterStockResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.StockMovementResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.CreateItemCommandFromResourceAssembler;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.ItemResourceFromEntityAssembler;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.RegisterStockCommandFromResourceAssembler;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.UpdateItemCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/items", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Items", description = "Item Management Endpoints")
public class ItemsController {
    private final ItemQueryService itemQueryService;
    private final ItemCommandService itemCommandService;

    public ItemsController(ItemQueryService itemQueryService, ItemCommandService itemCommandService) {
        this.itemQueryService = itemQueryService;
        this.itemCommandService = itemCommandService;
    }

    @PostMapping
    public ResponseEntity<ItemResource> createItem(@RequestBody CreateItemResource resource) {
        var command = CreateItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var itemId = this.itemCommandService.handle(command);

        if (itemId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getItemByIdQuery = new GetItemByIdQuery(itemId);
        var optionalItem = this.itemQueryService.handle(getItemByIdQuery);

        var itemResource = ItemResourceFromEntityAssembler.toResourceFromEntity(optionalItem.get());
        return new ResponseEntity<>(itemResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemResource>> getAllItems() {
        var getAllItemsQuery = new GetAllItemsQuery();
        var items = this.itemQueryService.handle(getAllItemsQuery);
        var itemResources = items.stream()
                .map(ItemResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemResources);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResource> getItemById(@PathVariable Long itemId) {
        var getItemByIdQuery = new GetItemByIdQuery(itemId);
        var optionalItem = this.itemQueryService.handle(getItemByIdQuery);

        return optionalItem.map(item -> ResponseEntity.ok(ItemResourceFromEntityAssembler.toResourceFromEntity(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResource> updateItem(@PathVariable Long itemId, @RequestBody ItemResource resource) {
        var updateItemCommand = UpdateItemCommandFromResourceAssembler.toCommandFromResource(itemId, resource);
        var optionalItem = this.itemCommandService.handle(updateItemCommand);

        return optionalItem.map(item -> ResponseEntity.ok(ItemResourceFromEntityAssembler.toResourceFromEntity(item)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        var deleteItemCommand = new DeleteItemCommand(itemId);
        this.itemCommandService.handle(deleteItemCommand);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/movements")
    public ResponseEntity<StockMovementResource> registerMovement(@RequestBody RegisterStockResource resource) {

        var command = RegisterStockCommandFromResourceAssembler.toCommandFromResource(resource);

        if (command instanceof RegisterStockEntryCommand entryCmd) {
            itemCommandService.handle(entryCmd);
        } else if (command instanceof RegisterStockExitCommand exitCmd) {
            itemCommandService.handle(exitCmd);
        } else if (command instanceof RegisterStockWriteOffCommand writeOffCmd) {
            itemCommandService.handle(writeOffCmd);
        } else if (command instanceof RegisterStockAdjustmentCommand adjCmd) {
            itemCommandService.handle(adjCmd);
        } else {
            throw new IllegalArgumentException("Unknown command type");
        }

        return ResponseEntity.ok().build();
    }
}