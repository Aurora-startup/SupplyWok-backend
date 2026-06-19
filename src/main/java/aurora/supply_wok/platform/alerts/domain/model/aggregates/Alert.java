package aurora.supply_wok.platform.alerts.domain.model.aggregates;

import aurora.supply_wok.platform.alerts.domain.model.valueobjects.EAlertSeverity;
import aurora.supply_wok.platform.alerts.domain.model.valueobjects.EAlertStatus;
import aurora.supply_wok.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "alerts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "alert_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Alert extends AuditableAbstractAggregateRoot<Alert> {

    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private EAlertSeverity severity;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "detail", nullable = false)
    private String detail;

    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EAlertStatus status;

    protected Alert() {
    }

    protected Alert(EAlertSeverity severity, String detail) {
        this.severity = severity;
        this.detail = detail;
        this.status = EAlertStatus.Pending;
    }

    public void acknowledge() {
        this.status = EAlertStatus.Acknowledged;
    }
}
