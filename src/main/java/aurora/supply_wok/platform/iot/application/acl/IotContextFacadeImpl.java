package aurora.supply_wok.platform.iot.application.acl;

import aurora.supply_wok.platform.iot.application.commandservices.SensorCommandService;
import aurora.supply_wok.platform.iot.application.queryservices.SensorQueryService;
import aurora.supply_wok.platform.iot.domain.model.commands.CreateSensorCommand;
import aurora.supply_wok.platform.iot.domain.model.queries.GetSensorByIdQuery;
import aurora.supply_wok.platform.iot.domain.model.valueobjects.ESensorType;
import aurora.supply_wok.platform.iot.interfaces.acl.IotContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application-layer implementation of the IoT ACL facade.
 */
@Service
public class IotContextFacadeImpl implements IotContextFacade {

    private final SensorCommandService sensorCommandService;
    private final SensorQueryService sensorQueryService;

    public IotContextFacadeImpl(SensorCommandService sensorCommandService, SensorQueryService sensorQueryService) {
        this.sensorCommandService = sensorCommandService;
        this.sensorQueryService = sensorQueryService;
    }

    @Override
    public Long createSensor(String name, double minValue, double maxValue, boolean enabled, double lastValue, String typeName) {
        try {
            var sensorType = ESensorType.valueOf(typeName);
            var createSensorCommand = new CreateSensorCommand(name, minValue, maxValue, enabled, lastValue, sensorType);
            return sensorCommandService.handle(createSensorCommand);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public String fetchSensorNameById(Long sensorId) {
        var query = new GetSensorByIdQuery(sensorId);
        var sensor = sensorQueryService.handle(query);
        return sensor.map(s -> s.getName()).orElse("");
    }

    @Override
    public boolean isSensorEnabled(Long sensorId) {
        var query = new GetSensorByIdQuery(sensorId);
        var sensor = sensorQueryService.handle(query);
        return sensor.map(s -> s.isEnabled()).orElse(false);
    }

    @Override
    public Optional<Double> fetchSensorLastValueById(Long sensorId) {
        var query = new GetSensorByIdQuery(sensorId);
        return sensorQueryService.handle(query).map(sensor -> sensor.getLastValue());
    }
}
