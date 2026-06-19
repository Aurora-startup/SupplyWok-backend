package aurora.supply_wok.platform.alerts.application.internal.outboundservices.acl;

import aurora.supply_wok.platform.iot.interfaces.acl.IotContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ACL service used by the Alerts bounded context to interact with IoT capabilities.
 */
@Service
public class ExternalIotService {

    private final IotContextFacade iotContextFacade;

    public ExternalIotService(IotContextFacade iotContextFacade) {
        this.iotContextFacade = iotContextFacade;
    }

    public Optional<String> fetchSensorNameById(Long sensorId) {
        return Optional.ofNullable(iotContextFacade.fetchSensorNameById(sensorId))
                .filter(name -> !name.isBlank());
    }

    public Optional<Double> fetchSensorLastValueById(Long sensorId) {
        return iotContextFacade.fetchSensorLastValueById(sensorId);
    }
}
