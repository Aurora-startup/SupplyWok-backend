package aurora.supply_wok.platform.profiles.infrastructure.persistence.jpa.adapters;

import aurora.supply_wok.platform.profiles.domain.model.aggregates.Profile;
import aurora.supply_wok.platform.profiles.domain.model.valueobjects.EProfileType;
import aurora.supply_wok.platform.profiles.domain.repositories.ProfileRepository;
import aurora.supply_wok.platform.profiles.infrastructure.persistence.jpa.assemblers.ProfilePersistenceAssembler;
import aurora.supply_wok.platform.profiles.infrastructure.persistence.jpa.repositories.ProfilePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository adapter that bridges the profile repository port with Spring Data JPA.
 */
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfilePersistenceRepository profilePersistenceRepository;

    public ProfileRepositoryImpl(ProfilePersistenceRepository profilePersistenceRepository) {
        this.profilePersistenceRepository = profilePersistenceRepository;
    }

    @Override
    public Optional<Profile> findByProfileType(EProfileType profileType) {
        return profilePersistenceRepository.findByProfileType(profileType)
                .map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Profile save(Profile profile) {
        var saved = profilePersistenceRepository.save(ProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        return ProfilePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
