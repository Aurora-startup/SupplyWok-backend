package aurora.supply_wok.platform.profiles.application.queryservices;

import aurora.supply_wok.platform.profiles.domain.model.aggregates.Profile;
import aurora.supply_wok.platform.profiles.domain.model.queries.GetProfileByTypeQuery;

import java.util.Optional;

/**
 * Application contract for profile read use cases.
 */
public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByTypeQuery query);
}
