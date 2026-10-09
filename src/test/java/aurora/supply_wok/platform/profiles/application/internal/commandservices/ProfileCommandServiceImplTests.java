package aurora.supply_wok.platform.profiles.application.internal.commandservices;

import aurora.supply_wok.platform.profiles.domain.model.aggregates.Profile;
import aurora.supply_wok.platform.profiles.domain.model.commands.UpdateProfileCommand;
import aurora.supply_wok.platform.profiles.domain.model.valueobjects.EProfileType;
import aurora.supply_wok.platform.profiles.domain.repositories.ProfileRepository;
import aurora.supply_wok.platform.suppliers.interfaces.acl.SuppliersContextFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileCommandServiceImplTests {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private SuppliersContextFacade suppliersContextFacade;

    @InjectMocks
    private ProfileCommandServiceImpl profileCommandService;

    @Test
    void updatingProfileForNewEmailLeavesExistingProfileUnchanged() {
        var profileA = new Profile(
                EProfileType.RESTAURANT,
                "Restaurant A",
                "Alice",
                "Smith",
                "a@example.com",
                "Street 1",
                "District 1",
                "Lima",
                "Peru",
                "999111222",
                true,
                false
        );
        profileA.setId(1L);

        when(profileRepository.findByProfileTypeAndEmail(EProfileType.RESTAURANT, "b@example.com"))
                .thenReturn(Optional.empty());
        lenient().when(profileRepository.findAllByProfileType(EProfileType.RESTAURANT))
                .thenReturn(List.of(profileA));
        when(profileRepository.save(any(Profile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var command = new UpdateProfileCommand(
                EProfileType.RESTAURANT,
                "Restaurant B",
                "Bob",
                "Jones",
                "b@example.com",
                "Street 2",
                "District 2",
                "Lima",
                "Peru",
                "999333444",
                true,
                false
        );

        var result = profileCommandService.handle(command);

        assertThat(profileA.getEmail())
                .as("Profile A must not be overwritten")
                .isEqualTo("a@example.com");
        assertThat(result.getId()).isNull();
        assertThat(result.getEmail()).isEqualTo("b@example.com");
        assertThat(result.getBusinessName()).isEqualTo("Restaurant B");
    }
}
