package aurora.supply_wok.platform.alerts.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateAlertRestaurantFromInventoryRequest",
        description = "Request payload for creating a restaurant alert from inventory stock differences",
        example = "{\"sensorId\": 1}"
)
public record CreateAlertRestaurantFromInventoryResource(
        @NotNull
        @Schema(description = "Sensor identifier", example = "1")
        Long sensorId
) {
}
