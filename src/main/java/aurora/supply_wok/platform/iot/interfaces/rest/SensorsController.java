package aurora.supply_wok.platform.iot.interfaces.rest;

import aurora.supply_wok.platform.iot.domain.model.queries.GetAllSensorsQuery;
import aurora.supply_wok.platform.iot.domain.model.queries.GetSensorByIdQuery;
import aurora.supply_wok.platform.iot.domain.services.SensorCommandService;
import aurora.supply_wok.platform.iot.domain.services.SensorQueryService;
import aurora.supply_wok.platform.iot.interfaces.rest.resources.CreateSensorResource;
import aurora.supply_wok.platform.iot.interfaces.rest.resources.SensorResource;
import aurora.supply_wok.platform.iot.interfaces.rest.transform.CreateSensorCommandFromResourceAssembler;
import aurora.supply_wok.platform.iot.interfaces.rest.transform.SensorResourceFromEntityAssembler;
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

/**
 * REST controller that exposes endpoints for managing sensors.
 * Provides endpoints for creating, retrieving, and listing sensors.
 */
@RestController
@RequestMapping(value = "/api/v1/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sensors", description = "Sensor management endpoints")
public class SensorsController {

    private final SensorCommandService sensorCommandService;
    private final SensorQueryService sensorQueryService;

    /**
     * Constructs the SensorsController.
     *
     * @param sensorCommandService the command service for sensors
     * @param sensorQueryService   the query service for sensors
     */
    public SensorsController(SensorCommandService sensorCommandService, SensorQueryService sensorQueryService) {
        this.sensorCommandService = sensorCommandService;
        this.sensorQueryService = sensorQueryService;
    }

    /**
     * Creates a new sensor.
     *
     * @param resource the create sensor request payload
     * @return the created sensor response payload
     */
    @PostMapping
    @Operation(
        summary = "Create a new sensor",
        description = "Creates a new sensor with specified attributes and validation."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Sensor created successfully",
            content = @Content(schema = @Schema(implementation = SensorResource.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<SensorResource> createSensor(@Valid @RequestBody CreateSensorResource resource) {
        var createSensorCommand = CreateSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        var sensorId = sensorCommandService.handle(createSensorCommand);
        
        var getSensorByIdQuery = new GetSensorByIdQuery(sensorId);
        var sensor = sensorQueryService.handle(getSensorByIdQuery);

        if (sensor.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var sensorResource = SensorResourceFromEntityAssembler.toResourceFromEntity(sensor.get());
        return new ResponseEntity<>(sensorResource, HttpStatus.CREATED);
    }

    /**
     * Retrieves a sensor by its identifier.
     *
     * @param sensorId the sensor unique identifier
     * @return the sensor details if found, otherwise not found status
     */
    @GetMapping("/{sensorId}")
    @Operation(
        summary = "Get sensor by ID",
        description = "Retrieves a specific sensor's information by unique identifier."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sensor found",
            content = @Content(schema = @Schema(implementation = SensorResource.class))
        ),
        @ApiResponse(responseCode = "404", description = "Sensor not found")
    })
    public ResponseEntity<SensorResource> getSensorById(
            @PathVariable
            @Parameter(description = "Sensor unique identifier", example = "1", required = true)
            Long sensorId
    ) {
        var getSensorByIdQuery = new GetSensorByIdQuery(sensorId);
        var sensor = sensorQueryService.handle(getSensorByIdQuery);
        
        if (sensor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var sensorResource = SensorResourceFromEntityAssembler.toResourceFromEntity(sensor.get());
        return ResponseEntity.ok(sensorResource);
    }

    /**
     * Retrieves a list of all sensors in the system.
     *
     * @return a list of all sensors
     */
    @GetMapping
    @Operation(
        summary = "Get all sensors",
        description = "Retrieves a list of all registered sensors."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sensors list retrieved",
            content = @Content(schema = @Schema(implementation = SensorResource.class))
        )
    })
    public ResponseEntity<List<SensorResource>> getAllSensors() {
        var sensors = sensorQueryService.handle(new GetAllSensorsQuery());
        if (sensors.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var sensorResources = sensors.stream()
                .map(SensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(sensorResources);
    }
}
