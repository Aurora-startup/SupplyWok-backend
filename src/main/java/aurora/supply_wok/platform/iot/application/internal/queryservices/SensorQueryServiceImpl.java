package aurora.supply_wok.platform.iot.application.internal.queryservices;

import aurora.supply_wok.platform.iot.domain.model.aggregates.Sensor;
import aurora.supply_wok.platform.iot.domain.model.queries.GetAllSensorsQuery;
import aurora.supply_wok.platform.iot.domain.model.queries.GetSensorByIdQuery;
import aurora.supply_wok.platform.iot.application.queryservices.SensorQueryService;
import aurora.supply_wok.platform.iot.infrastructure.persistence.jpa.repositories.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SensorQueryService interface.
 * Handles query-side actions for Sensor entities (find all, find by ID).
 */
@Service
public class SensorQueryServiceImpl implements SensorQueryService {

    private final SensorRepository sensorRepository;

    /**
     * Constructs the SensorQueryServiceImpl.
     *
     * @param sensorRepository the repository for Sensor queries
     */
    public SensorQueryServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    /**
     * Handles the retrieval of all Sensors.
     *
     * @param query the GetAllSensorsQuery
     * @return a List of all Sensors
     */
    @Override
    public List<Sensor> handle(GetAllSensorsQuery query) {
        return this.sensorRepository.findAll();
    }

    /**
     * Handles the retrieval of a Sensor by its ID.
     *
     * @param query the GetSensorByIdQuery containing the sensor ID
     * @return an Optional containing the Sensor if found, otherwise empty
     */
    @Override
    public Optional<Sensor> handle(GetSensorByIdQuery query) {
        return this.sensorRepository.findById(query.sensorId());
    }
}
