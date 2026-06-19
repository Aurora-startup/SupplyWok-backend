package aurora.supply_wok.platform.alerts.application.internal.commandservices;

import aurora.supply_wok.platform.alerts.application.commandservices.AlertCommandService;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.RestaurantAlert;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.SupplierAlert;
import aurora.supply_wok.platform.alerts.domain.model.commands.CreateRestaurantAlertCommand;
import aurora.supply_wok.platform.alerts.domain.model.commands.CreateSupplierAlertCommand;
import aurora.supply_wok.platform.alerts.infrastructure.persistence.jpa.repositories.AlertRepository;
import org.springframework.stereotype.Service;

@Service
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertRepository alertRepository;

    public AlertCommandServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Long handle(CreateSupplierAlertCommand command) {
        var supplierAlert = new SupplierAlert(command);
        try {
            alertRepository.save(supplierAlert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving supplier alert: " + e.getMessage());
        }
        return supplierAlert.getId();
    }

    @Override
    public Long handle(CreateRestaurantAlertCommand command) {
        var restaurantAlert = new RestaurantAlert(command);
        try {
            alertRepository.save(restaurantAlert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving restaurant alert: " + e.getMessage());
        }
        return restaurantAlert.getId();
    }
}
