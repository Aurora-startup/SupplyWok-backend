package aurora.supply_wok.platform.iam.application.internal.eventhandlers;

import aurora.supply_wok.platform.iam.application.commandservices.RoleCommandService;
import aurora.supply_wok.platform.iam.application.internal.startup.RoleDataMigrationService;
import aurora.supply_wok.platform.iam.domain.model.commands.SeedRolesCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Application lifecycle handler that ensures IAM roles are seeded when the application is ready.
 */
@Service
@Slf4j
public class ApplicationReadyEventHandler {
    private final RoleCommandService roleCommandService;
    private final RoleDataMigrationService roleDataMigrationService;

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService, RoleDataMigrationService roleDataMigrationService) {
        this.roleCommandService = roleCommandService;
        this.roleDataMigrationService = roleDataMigrationService;
    }

    /**
     * Handles the Spring application-ready event and triggers role seeding verification.
     *
     * @param event Spring Boot readiness event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        log.info("Starting IAM role migration and seeding for {} at {}", applicationName, currentTimestamp());
        roleDataMigrationService.migrateLegacyRoles();
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.handle(seedRolesCommand);
        log.info("IAM role migration and seeding finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
