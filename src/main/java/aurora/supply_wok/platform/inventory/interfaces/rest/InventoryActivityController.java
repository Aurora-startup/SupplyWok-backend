package aurora.supply_wok.platform.inventory.interfaces.rest;

import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllInventoryActivitiesQuery;
import aurora.supply_wok.platform.inventory.domain.services.InventoryQueryService;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.InventoryActivityResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.InventoryActivityResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET })
@RestController
@RequestMapping(value = "/api/v1/inventory/activity", produces = "application/json")
@Tag(name = "Inventory Activity", description = "Global Inventory Audit Log")
public class InventoryActivityController {

    private final InventoryQueryService inventoryQueryService;

    public InventoryActivityController(InventoryQueryService inventoryQueryService) {
        this.inventoryQueryService = inventoryQueryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryActivityResource>> getAllActivity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        var query = new GetAllInventoryActivitiesQuery(page, size);
        var logs = this.inventoryQueryService.handle(query);

        var resources = logs.stream()
                .map(InventoryActivityResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }
}