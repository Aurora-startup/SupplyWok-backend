package aurora.supply_wok.platform.inventory.interfaces.rest;

import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllStockMovementsByItemIdQuery;
import aurora.supply_wok.platform.inventory.domain.services.InventoryCommandService;
import aurora.supply_wok.platform.inventory.domain.services.InventoryQueryService;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.StockMovementResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.StockMovementResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST })
@RestController
@RequestMapping(value = "/api/v1/stock-movements", produces = "application/json")
@Tag(name = "Stock Movements", description = "Stock Movement Operations")
public class StockMovementsController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;

    public StockMovementsController(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }

    @GetMapping
    public ResponseEntity<List<StockMovementResource>> getStockMovementsByItemId(
            @RequestParam(name = "itemId") Long itemId) {

        var query = new GetAllStockMovementsByItemIdQuery(itemId);

        var movements = this.inventoryQueryService.handle(query);

        var resources = movements.stream()
                .map(StockMovementResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}