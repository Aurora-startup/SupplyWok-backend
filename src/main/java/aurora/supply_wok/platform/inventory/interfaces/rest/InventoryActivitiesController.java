package aurora.supply_wok.platform.inventory.interfaces.rest;

import aurora.supply_wok.platform.inventory.application.queryservices.InventoryQueryService;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllInventoryActivitiesQuery;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.InventoryActivityResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.InventoryActivityResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET })
@RestController
@RequestMapping(value = {"/api/v1/inventory/activities", "/api/v1/inventory/activity"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inventory Activities", description = "Global inventory audit log endpoints")
public class InventoryActivitiesController {

    private final InventoryQueryService inventoryQueryService;

    public InventoryActivitiesController(InventoryQueryService inventoryQueryService) {
        this.inventoryQueryService = inventoryQueryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryActivityResource>> getAllActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        var query = new GetAllInventoryActivitiesQuery(page, size);
        var logs = this.inventoryQueryService.handle(query);

        var resources = logs.stream()
                .map(InventoryActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}
