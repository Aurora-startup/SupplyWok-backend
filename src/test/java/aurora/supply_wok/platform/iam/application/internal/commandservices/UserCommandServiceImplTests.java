package aurora.supply_wok.platform.iam.application.internal.commandservices;

import aurora.supply_wok.platform.iam.application.internal.outboundservices.hashing.HashingService;
import aurora.supply_wok.platform.iam.application.internal.outboundservices.tokens.TokenService;
import aurora.supply_wok.platform.iam.domain.model.aggregates.User;
import aurora.supply_wok.platform.iam.domain.model.commands.SignUpCommand;
import aurora.supply_wok.platform.iam.domain.model.valueobjects.Roles;
import aurora.supply_wok.platform.iam.domain.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import aurora.supply_wok.platform.shared.application.result.ApplicationError;
import aurora.supply_wok.platform.shared.application.result.Result;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HashingService hashingService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserCommandServiceImpl userCommandService;

    @Test
    void signUpWithRoleAdminFails() {
        var command = new SignUpCommand("admin@supplywok.com", "SecretPass123!", "ADMIN");
        when(userRepository.existsByEmail("admin@supplywok.com")).thenReturn(false);
        lenient().when(hashingService.encode("SecretPass123!")).thenReturn("hashedPassword");
        lenient().when(userRepository.findByEmail("admin@supplywok.com"))
                .thenReturn(Optional.of(new User("admin@supplywok.com", "hashedPassword", Roles.ADMIN)));

        var result = userCommandService.handle(command);

        assertThat(result.isFailure()).isTrue();
        assertThat(result).isInstanceOf(Result.Failure.class);
        var failure = (Result.Failure<User, ?>) result;
        assertThat(failure.error()).isInstanceOf(aurora.supply_wok.platform.shared.application.result.ApplicationError.class);
        assertThat(((aurora.supply_wok.platform.shared.application.result.ApplicationError) failure.error()).details())
                .isEqualTo("Role not allowed for sign-up");
        verify(userRepository, never()).save(any());
    }

    @Test
    void signUpWithRoleRestaurantSucceeds() {
        var command = new SignUpCommand("restaurant@supplywok.com", "SecretPass123!", "RESTAURANT");
        when(userRepository.existsByEmail("restaurant@supplywok.com")).thenReturn(false);
        when(hashingService.encode("SecretPass123!")).thenReturn("hashedPassword");
        var savedUser = new User("restaurant@supplywok.com", "hashedPassword", Roles.RESTAURANT);
        when(userRepository.findByEmail("restaurant@supplywok.com")).thenReturn(Optional.of(savedUser));

        var result = userCommandService.handle(command);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result).isInstanceOf(Result.Success.class);
        var success = (Result.Success<User, ?>) result;
        assertThat(success.value().getEmail()).isEqualTo("restaurant@supplywok.com");
        assertThat(success.value().getRole()).isEqualTo(Roles.RESTAURANT);
        verify(userRepository).save(any(User.class));
    }
}
