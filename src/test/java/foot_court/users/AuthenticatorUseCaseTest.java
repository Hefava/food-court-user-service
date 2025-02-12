package foot_court.users;

import foot_court.users.domain.api.usecase.AuthenticatorUseCase;
import foot_court.users.domain.model.User;
import foot_court.users.domain.spi.IAuthenticationPersistencePort;
import foot_court.users.domain.utils.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticatorUseCaseTest {

    @Mock
    private IAuthenticationPersistencePort authenticationPort;

    @InjectMocks
    private AuthenticatorUseCase authenticatorUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        String email = "test@example.com";
        String password = "password123";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        String expectedToken = "generated-token";

        when(authenticationPort.authenticate(email, password)).thenReturn(user);
        when(authenticationPort.generateToken(user)).thenReturn(expectedToken);

        String actualToken = authenticatorUseCase.login(email, password);

        assertEquals(expectedToken, actualToken, "El token generado no coincide con el esperado");
        verify(authenticationPort, times(1)).authenticate(email, password);
        verify(authenticationPort, times(1)).generateToken(user);
    }

    @Test
    void testValidateToken() {
        String token = "valid-token";
        Validation expectedValidation = new Validation("testUser", "USER_ROLE", true);

        when(authenticationPort.validateToken(token)).thenReturn(expectedValidation);

        Validation actualValidation = authenticatorUseCase.validateToken(token);

        assertEquals(expectedValidation, actualValidation, "La validación del token no coincide con la esperada");
        verify(authenticationPort, times(1)).validateToken(token);
    }

    @Test
    void testLoginWithInvalidCredentials() {
        String email = "invalid@example.com";
        String password = "wrongpassword";

        when(authenticationPort.authenticate(email, password)).thenReturn(null);

        String actualToken = authenticatorUseCase.login(email, password);

        assertEquals(null, actualToken, "El token debería ser nulo para credenciales inválidas");
        verify(authenticationPort, times(1)).authenticate(email, password);
        verify(authenticationPort, never()).generateToken(any());
    }

    @Test
    void testValidateTokenWithInvalidToken() {
        String token = "invalid-token";
        Validation expectedValidation = new Validation("", "", false);

        when(authenticationPort.validateToken(token)).thenReturn(expectedValidation);

        Validation actualValidation = authenticatorUseCase.validateToken(token);

        assertEquals(expectedValidation, actualValidation, "La validación del token no coincide con la esperada");
        verify(authenticationPort, times(1)).validateToken(token);
    }
}