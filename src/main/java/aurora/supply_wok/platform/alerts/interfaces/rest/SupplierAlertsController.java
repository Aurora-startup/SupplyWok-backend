package aurora.supply_wok.platform.alerts.interfaces.rest;

import aurora.supply_wok.platform.alerts.application.commandservices.AlertCommandService;
import aurora.supply_wok.platform.alerts.application.queryservices.AlertQueryService;
import aurora.supply_wok.platform.alerts.domain.model.queries.GetAlertByIdQuery;
import aurora.supply_wok.platform.alerts.domain.model.queries.GetAllSupplierAlertsQuery;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.AlertResource;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.CreateSupplierAlertResource;
import aurora.supply_wok.platform.alerts.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import aurora.supply_wok.platform.alerts.interfaces.rest.transform.CreateSupplierAlertCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/supplier/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Supplier Alerts", description = "Supplier Alerts management endpoints")
public class SupplierAlertsController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public SupplierAlertsController(AlertCommandService alertCommandService, AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new supplier alert", description = "Creates a new supplier alert.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Supplier alert created successfully", content = @Content(schema = @Schema(implementation = AlertResource.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<AlertResource> createSupplierAlert(@Valid @RequestBody CreateSupplierAlertResource resource) {
        try {
            var command = CreateSupplierAlertCommandFromResourceAssembler.toCommandFromResource(resource);
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
    @Operation(summary = "Get supplier alert by ID", description = "Retrieves a specific supplier alert.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Supplier alert found", content = @Content(schema = @Schema(implementation = AlertResource.class))),
        @ApiResponse(responseCode = "404", description = "Supplier alert not found")
    })
    public ResponseEntity<AlertResource> getSupplierAlertById(
            @PathVariable @Parameter(description = "Supplier Alert ID", example = "1", required = true) Long alertId
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
    @Operation(summary = "Get all supplier alerts", description = "Retrieves a list of all registered supplier alerts.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Supplier alerts retrieved successfully")
    })
    public ResponseEntity<List<AlertResource>> getAllSupplierAlerts() {
        var alerts = alertQueryService.handle(new GetAllSupplierAlertsQuery());
        if (alerts.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var resources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
