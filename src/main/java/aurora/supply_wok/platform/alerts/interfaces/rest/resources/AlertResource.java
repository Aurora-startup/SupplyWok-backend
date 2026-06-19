package aurora.supply_wok.platform.alerts.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(
    name = "AlertResponse",
    description = "Alert details response payload",
    example = "{\"id\": 1, \"severity\": \"Critical\", \"detail\": \"Sensor value exceeded limit.\", \"status\": \"Pending\", \"createdAt\": \"2026-06-19T03:30:00.000+00:00\", \"alertType\": \"RESTAURANT\", \"sensorId\": 1, \"sensorName\": \"Kitchen Temp Sensor\"}"
)
public record AlertResource(
    @Schema(description = "Alert unique identifier", example = "1")
    Long id,

    @Schema(description = "Alert severity level", example = "Critical")
    String severity,

    @Schema(description = "Alert details", example = "Sensor value exceeded safety limit.")
    String detail,

    @Schema(description = "Alert status", example = "Pending")
    String status,

    @Schema(description = "Alert creation timestamp")
    Date createdAt,

    @Schema(description = "Alert type", example = "RESTAURANT")
    String alertType,

    @Schema(description = "Sensor identifier (only for restaurant alerts)", example = "1", nullable = true)
    Long sensorId,

    @Schema(description = "Sensor name (only for restaurant alerts)", example = "Kitchen Temp Sensor", nullable = true)
    String sensorName
) {
}
