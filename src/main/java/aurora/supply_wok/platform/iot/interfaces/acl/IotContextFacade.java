package aurora.supply_wok.platform.iot.interfaces.acl;

import aurora.supply_wok.platform.iot.domain.model.commands.CreateSensorCommand;
import aurora.supply_wok.platform.iot.domain.model.queries.GetSensorByIdQuery;
import aurora.supply_wok.platform.iot.domain.model.valueobjects.ESensorType;
import aurora.supply_wok.platform.iot.application.commandservices.SensorCommandService;
import aurora.supply_wok.platform.iot.application.queryservices.SensorQueryService;
import org.springframework.stereotype.Service;

/**
 * ACL facade that exposes IoT bounded context capabilities to other bounded contexts.
 *
 * <p>Provides a simplified, decoupled integration surface for creating and querying sensors
 * without leaking IoT internal domain models or entities.</p>
 */
@Service
public class IotContextFacade {

    private final SensorCommandService sensorCommandService;
    private final SensorQueryService sensorQueryService;

    /**
     * Constructs the IotContextFacade.
     *
     * @param sensorCommandService the command service for sensors
     * @param sensorQueryService   the query service for sensors
     */
    public IotContextFacade(SensorCommandService sensorCommandService, SensorQueryService sensorQueryService) {
        this.sensorCommandService = sensorCommandService;
        this.sensorQueryService = sensorQueryService;
    }

    /**
     * Creates a new sensor.
     *
     * @param name      the name of the sensor
     * @param minValue  the minimum value the sensor can record
     * @param maxValue  the maximum value the sensor can record
     * @param enabled   whether the sensor is active
     * @param lastValue the last recorded value
     * @param typeName  the sensor type name (e.g. "Temperature", "Humidity", "Weight")
     * @return the created sensor identifier, or {@code 0L} when creation fails
     */
    public Long createSensor(String name, double minValue, double maxValue, boolean enabled, double lastValue, String typeName) {
        try {
            var sensorType = ESensorType.valueOf(typeName);
            var createSensorCommand = new CreateSensorCommand(name, minValue, maxValue, enabled, lastValue, sensorType);
            return sensorCommandService.handle(createSensorCommand);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * Fetches the name of a sensor by its identifier.
     *
     * @param sensorId the sensor identifier
     * @return the name of the sensor, or an empty string if not found
     */
    public String fetchSensorNameById(Long sensorId) {
        var query = new GetSensorByIdQuery(sensorId);
        var sensor = sensorQueryService.handle(query);
        return sensor.map(s -> s.getName()).orElse("");
    }

    /**
     * Checks if a sensor is enabled.
     *
     * @param sensorId the sensor identifier
     * @return {@code true} if enabled, {@code false} otherwise (or if not found)
     */
    public boolean isSensorEnabled(Long sensorId) {
        var query = new GetSensorByIdQuery(sensorId);
        var sensor = sensorQueryService.handle(query);
        return sensor.map(s -> s.isEnabled()).orElse(false);
    }
}
