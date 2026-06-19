package aurora.supply_wok.platform.alerts.interfaces.rest.transform;

import aurora.supply_wok.platform.alerts.domain.model.aggregates.Alert;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.RestaurantAlert;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.SupplierAlert;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {

    public static AlertResource toResourceFromEntity(Alert entity) {
        if (entity instanceof RestaurantAlert restaurantAlert) {
            return new AlertResource(
                    restaurantAlert.getId(),
                    restaurantAlert.getSeverity().name(),
                    restaurantAlert.getDetail(),
                    restaurantAlert.getStatus().name(),
                    restaurantAlert.getCreatedAt(),
                    "RESTAURANT",
                    restaurantAlert.getSensorId(),
                    restaurantAlert.getSensorName()
            );
        } else if (entity instanceof SupplierAlert supplierAlert) {
            return new AlertResource(
                    supplierAlert.getId(),
                    supplierAlert.getSeverity().name(),
                    supplierAlert.getDetail(),
                    supplierAlert.getStatus().name(),
                    supplierAlert.getCreatedAt(),
                    "SUPPLIER",
                    null,
                    null
            );
        }
        throw new IllegalArgumentException("Unknown alert type: " + entity.getClass().getName());
    }
}
