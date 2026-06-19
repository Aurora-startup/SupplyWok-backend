package aurora.supply_wok.platform.alerts.application.internal.commandservices;

import aurora.supply_wok.platform.alerts.application.commandservices.AlertCommandService;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.Alert;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.RestaurantAlert;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.SupplierAlert;
import aurora.supply_wok.platform.alerts.domain.model.commands.AcknowledgeAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.commands.CreateAlertRestaurantFromInventoryCommand;
import aurora.supply_wok.platform.alerts.domain.model.commands.CreateRestaurantAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.commands.CreateSupplierAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.valueobjects.EAlertSeverity;
import aurora.supply_wok.platform.alerts.application.internal.outboundservices.acl.ExternalInventoryService;
import aurora.supply_wok.platform.alerts.application.internal.outboundservices.acl.ExternalIotService;
import aurora.supply_wok.platform.alerts.infrastructure.persistence.jpa.repositories.AlertPersistenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertPersistenceRepository alertPersistenceRepository;
    private final ExternalIotService externalIotService;
    private final ExternalInventoryService externalInventoryService;

    public AlertCommandServiceImpl(AlertPersistenceRepository alertPersistenceRepository,
                                   ExternalIotService externalIotService,
                                   ExternalInventoryService externalInventoryService) {
        this.alertPersistenceRepository = alertPersistenceRepository;
        this.externalIotService = externalIotService;
        this.externalInventoryService = externalInventoryService;
    }

    @Override
    public Long handle(CreateSupplierAlertCommand command) {
        var supplierAlert = new SupplierAlert(command);
        try {
            alertPersistenceRepository.save(supplierAlert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving supplier alert: " + e.getMessage());
        }
        return supplierAlert.getId();
    }

    @Override
    public Long handle(CreateRestaurantAlertCommand command) {
        var sensorName = externalIotService.fetchSensorNameById(command.sensorId());
        if (sensorName.isEmpty()) {
            throw new IllegalArgumentException("Sensor with ID " + command.sensorId() + " does not exist.");
        }

        var restaurantAlert = new RestaurantAlert(command.severity(), command.detail(), command.sensorId(), sensorName.get());
        try {
            alertPersistenceRepository.save(restaurantAlert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving restaurant alert: " + e.getMessage());
        }
        return restaurantAlert.getId();
    }

    @Override
    public Optional<Alert> handle(CreateAlertRestaurantFromInventoryCommand command) {
        var sensorLastValue = externalIotService.fetchSensorLastValueById(command.sensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor with ID " + command.sensorId() + " does not exist."));
        var sensorName = externalIotService.fetchSensorNameById(command.sensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor with ID " + command.sensorId() + " does not exist."));
        var inventoryStock = externalInventoryService.getTotalSupplyStock();

        if (Math.abs(inventoryStock - sensorLastValue) < 0.000001d) {
            return Optional.empty();
        }

        var detail = "Inventory stock (" + inventoryStock + ") differs from sensor last value (" + sensorLastValue + ").";
        var restaurantAlert = new RestaurantAlert(EAlertSeverity.Medium, detail, command.sensorId(), sensorName);
        try {
            alertPersistenceRepository.save(restaurantAlert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving restaurant alert: " + e.getMessage());
        }
        return Optional.of(restaurantAlert);
    }

    @Override
    public Optional<Alert> handle(AcknowledgeAlertCommand command) {
        var alert = alertPersistenceRepository.findById(command.alertId());
        if (alert.isEmpty()) {
            return Optional.empty();
        }

        alert.get().acknowledge();
        try {
            alertPersistenceRepository.save(alert.get());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while acknowledging alert: " + e.getMessage());
        }
        return alert;
    }
}
