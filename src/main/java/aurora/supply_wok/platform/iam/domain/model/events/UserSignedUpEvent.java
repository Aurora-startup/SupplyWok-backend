package aurora.supply_wok.platform.iam.domain.model.events;

import aurora.supply_wok.platform.iam.domain.model.aggregates.User;
import aurora.supply_wok.platform.iam.domain.model.entities.Role;

import java.util.List;

/**
 * Domain event raised when a user account is created.
 */
public record UserSignedUpEvent(
        Long userId,
        String email,
        List<String> roles
) {
    public static UserSignedUpEvent from(User user) {
        return new UserSignedUpEvent(
                user.getId(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getStringName).toList()
        );
    }
}
