package aurora.supply_wok.platform.alerts.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
    name = "CreateSupplierAlertRequest",
    description = "Request payload for creating a new supplier alert",
    example = "{\"severity\": \"High\", \"detail\": \"Supplier has delayed a critical shipment.\"}"
)
public record CreateSupplierAlertResource(
    @NotBlank
    @Schema(description = "Alert severity level", example = "High", allowableValues = {"Low", "Medium", "High", "Critical"})
    String severity,

    @NotBlank
    @Schema(description = "Detail about the alert", example = "Supplier has delayed a critical shipment.")
    String detail
) {
}
