package aurora.supply_wok.platform.profiles.application.internal.commandservices;

import aurora.supply_wok.platform.profiles.application.commandservices.ProfileCommandService;
import aurora.supply_wok.platform.profiles.domain.model.aggregates.Profile;
import aurora.supply_wok.platform.profiles.domain.model.commands.UpdateProfileCommand;
import aurora.supply_wok.platform.profiles.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

/**
 * Default profile command service implementation.
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile handle(UpdateProfileCommand command) {
        var existingProfile = profileRepository.findByProfileType(command.profileType());
        var profile = existingProfile.orElseGet(() -> new Profile(command.profileType()));
        profile.update(command);
        if (existingProfile.isPresent()) {
            profile.onUpdated();
        }
        return profileRepository.save(profile);
    }
}
