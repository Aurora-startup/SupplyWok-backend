package aurora.supply_wok.platform.alerts.interfaces.rest;

import aurora.supply_wok.platform.alerts.application.commandservices.AlertCommandService;
import aurora.supply_wok.platform.alerts.application.queryservices.AlertQueryService;
import aurora.supply_wok.platform.alerts.domain.model.queries.GetAlertByIdQuery;
import aurora.supply_wok.platform.alerts.domain.model.queries.GetAllRestaurantAlertsQuery;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.AlertResource;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.CreateRestaurantAlertResource;
import aurora.supply_wok.platform.alerts.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import aurora.supply_wok.platform.alerts.interfaces.rest.transform.CreateRestaurantAlertCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/restaurant/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Restaurant Alerts", description = "Restaurant Alerts management endpoints")
public class RestaurantAlertsController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public RestaurantAlertsController(AlertCommandService alertCommandService, AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new restaurant alert", description = "Creates a new restaurant alert.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurant alert created successfully", content = @Content(schema = @Schema(implementation = AlertResource.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<AlertResource> createRestaurantAlert(@Valid @RequestBody CreateRestaurantAlertResource resource) {
        try {
            var command = CreateRestaurantAlertCommandFromResourceAssembler.toCommandFromResource(resource);
            var alertId = alertCommandService.handle(command);

            var query = new GetAlertByIdQuery(alertId);
            var alert = alertQueryService.handle(query);

            if (alert.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
            return new ResponseEntity<>(alertResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{alertId}")
    @Operation(summary = "Get restaurant alert by ID", description = "Retrieves a specific restaurant alert.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant alert found", content = @Content(schema = @Schema(implementation = AlertResource.class))),
        @ApiResponse(responseCode = "404", description = "Restaurant alert not found")
    })
    public ResponseEntity<AlertResource> getRestaurantAlertById(
            @PathVariable @Parameter(description = "Restaurant Alert ID", example = "1", required = true) Long alertId
    ) {
        var query = new GetAlertByIdQuery(alertId);
        var alert = alertQueryService.handle(query);

        if (alert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(alertResource);
    }

    @GetMapping
    @Operation(summary = "Get all restaurant alerts", description = "Retrieves a list of all registered restaurant alerts.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant alerts retrieved successfully")
    })
    public ResponseEntity<List<AlertResource>> getAllRestaurantAlerts() {
        var alerts = alertQueryService.handle(new GetAllRestaurantAlertsQuery());
        if (alerts.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var resources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
