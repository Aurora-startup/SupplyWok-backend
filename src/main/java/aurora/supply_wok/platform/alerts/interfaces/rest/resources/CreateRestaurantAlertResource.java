package aurora.supply_wok.platform.alerts.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
    name = "CreateRestaurantAlertRequest",
    description = "Request payload for creating a new restaurant alert",
    example = "{\"severity\": \"Critical\", \"detail\": \"Sensor value exceeded safety limit.\", \"sensorId\": 1}"
)
public record CreateRestaurantAlertResource(
    @NotBlank
    @Schema(description = "Alert severity level", example = "Critical", allowableValues = {"Low", "Medium", "High", "Critical"})
    String severity,

    @NotBlank
    @Schema(description = "Detail about the alert", example = "Sensor value exceeded safety limit.")
    String detail,

    @NotNull
    @Schema(description = "Sensor identifier", example = "1")
    Long sensorId
) {
}
