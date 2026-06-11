package aurora.supply_wok.platform.iot.application.internal.commandservices;

import aurora.supply_wok.platform.iot.domain.model.aggregates.Sensor;
import aurora.supply_wok.platform.iot.domain.model.commands.CreateSensorCommand;
import aurora.supply_wok.platform.iot.domain.model.commands.DeleteSensorCommand;
import aurora.supply_wok.platform.iot.domain.model.commands.UpdateSensorCommand;
import aurora.supply_wok.platform.iot.domain.services.SensorCommandService;
import aurora.supply_wok.platform.iot.infrastructure.persistence.jpa.repositories.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the SensorCommandService interface.
 * Handles command-side actions for Sensor entities (create, update, delete).
 */
@Service
public class SensorCommandServiceImpl implements SensorCommandService {

    private final SensorRepository sensorRepository;

    /**
     * Constructs the SensorCommandServiceImpl.
     *
     * @param sensorRepository the repository for Sensor persistence
     */
    public SensorCommandServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    /**
     * Handles the creation of a new Sensor.
     *
     * @param command createSensorCommand containing sensor details
     * @return the ID of the newly created Sensor
     * @throws IllegalArgumentException if an error occurs while saving the sensor
     */
    @Override
    public Long handle(CreateSensorCommand command) {
        var sensor = new Sensor(command);
        try {
            sensorRepository.save(sensor);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving sensor: " + e.getMessage());
        }
        return sensor.getId();
    }

    /**
     * Handles the update of an existing Sensor.
     *
     * @param command the UpdateSensorCommand containing updated sensor details
     * @return an Optional containing the updated Sensor if found and saved, otherwise empty
     * @throws IllegalArgumentException if the sensor does not exist or an error occurs during save
     */
    @Override
    public Optional<Sensor> handle(UpdateSensorCommand command) {
        var result = sensorRepository.findById(command.sensorId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Sensor with ID " + command.sensorId() + " does not exist");
        }
        var sensor = result.get();
        sensor.updateSensor(command);
        try {
            sensorRepository.save(sensor);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating sensor: " + e.getMessage());
        }
        return Optional.of(sensor);
    }

    /**
     * Handles the deletion of a Sensor.
     *
     * @param command the DeleteSensorCommand containing the sensor ID to delete
     * @throws IllegalArgumentException if the sensor does not exist or an error occurs during deletion
     */
    @Override
    public void handle(DeleteSensorCommand command) {
        if (!sensorRepository.existsById(command.sensorId())) {
            throw new IllegalArgumentException("Sensor with ID " + command.sensorId() + " does not exist");
        }
        try {
            sensorRepository.deleteById(command.sensorId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting sensor: " + e.getMessage());
        }
    }
}
