package aurora.supply_wok.platform.iam.domain.repositories;

import aurora.supply_wok.platform.iam.domain.model.valueobjects.Roles;
import aurora.supply_wok.platform.iam.domain.model.entities.Role;

import java.util.List;
import java.util.Optional;

/**
 * IAM role repository port.
 */
public interface RoleRepository {
    Optional<Role> findByName(Roles name);

    List<Role> findAll();

    Role save(Role role);

    boolean existsByName(Roles name);
}

