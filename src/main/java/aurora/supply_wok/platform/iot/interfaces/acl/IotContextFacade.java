package aurora.supply_wok.platform.iot.interfaces.acl;

import java.util.Optional;

/**
 * ACL facade that exposes IoT bounded context capabilities to other bounded contexts.
 *
 * <p>Provides a simplified, decoupled integration surface for creating and querying sensors
 * without leaking IoT internal domain models or entities.</p>
 */
public interface IotContextFacade {
    Long createSensor(String name, double minValue, double maxValue, boolean enabled, double lastValue, String typeName);

    String fetchSensorNameById(Long sensorId);

    boolean isSensorEnabled(Long sensorId);

    Optional<Double> fetchSensorLastValueById(Long sensorId);
}
