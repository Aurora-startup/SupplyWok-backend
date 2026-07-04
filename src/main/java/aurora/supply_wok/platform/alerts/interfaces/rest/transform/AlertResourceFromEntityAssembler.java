package aurora.supply_wok.platform.alerts.interfaces.rest.transform;

import aurora.supply_wok.platform.alerts.domain.model.aggregates.RestaurantAlert;
import aurora.supply_wok.platform.alerts.domain.model.aggregates.SupplierAlert;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.RestaurantAlertResource;
import aurora.supply_wok.platform.alerts.interfaces.rest.resources.SupplierAlertResource;

public class AlertResourceFromEntityAssembler {

    public static RestaurantAlertResource toRestaurantResourceFromEntity(RestaurantAlert restaurantAlert) {
        return new RestaurantAlertResource(
                restaurantAlert.getId(),
                restaurantAlert.getSeverity().name(),
                restaurantAlert.getDetail(),
                restaurantAlert.getStatus().name(),
                restaurantAlert.getCreatedAt(),
                "RESTAURANT",
                restaurantAlert.getSensorId(),
                restaurantAlert.getSensorName()
        );
    }

    public static SupplierAlertResource toSupplierResourceFromEntity(SupplierAlert supplierAlert) {
        return new SupplierAlertResource(
                supplierAlert.getId(),
                supplierAlert.getSeverity().name(),
                supplierAlert.getDetail(),
                supplierAlert.getStatus().name(),
                supplierAlert.getCreatedAt(),
                "SUPPLIER"
        );
    }
}
