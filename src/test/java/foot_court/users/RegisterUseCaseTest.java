package foot_court.users;

import foot_court.users.domain.api.usecase.RegisterUseCase;
import foot_court.users.domain.exception.MultipleUserValidationExceptions;
import foot_court.users.domain.model.User;
import foot_court.users.domain.spi.IEncryptPasswordPort;
import foot_court.users.domain.spi.IUserPersistencePort;
import foot_court.users.domain.utils.UserUtils;
import foot_court.users.domain.utils.UserValidationMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUseCaseTest {

    @Mock
    private IEncryptPasswordPort encryptPasswordPort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private RegisterUseCase registerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerOwner_ShouldRegisterUserAndSetRole() {
        User user = createValidUser();
        when(encryptPasswordPort.encrypt(anyString())).thenReturn("encryptedPassword");
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        registerUseCase.registerOwner(user);

        assertEquals(UserUtils.ROLE_OWNER_ID, user.getRole());
        verify(userPersistencePort).saveUser(user);
        verify(encryptPasswordPort).encrypt(anyString());
    }

    @Test
    void registerEmployed_ShouldRegisterUserAndSetRole() {
        User user = createValidUser();
        Long restaurantId = 1L;
        Long ownerId = 2L;
        when(encryptPasswordPort.encrypt(anyString())).thenReturn("encryptedPassword");
        when(userPersistencePort.getUserId()).thenReturn(ownerId);
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        registerUseCase.registerEmployed(restaurantId, user);

        assertEquals(UserUtils.ROLE_EMPLOYED_ID, user.getRole());
        verify(encryptPasswordPort).encrypt(anyString());
    }

    @Test
    void registerCustomer_ShouldRegisterUserAndSetRole() {
        User user = createValidUser();
        when(encryptPasswordPort.encrypt(anyString())).thenReturn("encryptedPassword");
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        registerUseCase.registerCustomer(user);

        assertEquals(UserUtils.ROLE_CUSTOMER_ID, user.getRole());
        verify(userPersistencePort).saveUser(user);
        verify(encryptPasswordPort).encrypt(anyString());
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() {
        User user = createValidUser();
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(true);

        MultipleUserValidationExceptions exception = assertThrows(
                MultipleUserValidationExceptions.class,
                () -> registerUseCase.registerOwner(user)
        );

        assertTrue(exception.getErrors().contains(UserValidationMessages.USER_ALREADY_EXISTS));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailIsInvalid() {
        User user = createValidUser();
        user.setEmail("invalid-email");
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        MultipleUserValidationExceptions exception = assertThrows(
                MultipleUserValidationExceptions.class,
                () -> registerUseCase.registerOwner(user)
        );

        assertTrue(exception.getErrors().contains(UserValidationMessages.INVALID_EMAIL_FORMAT));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserIsUnderage() {
        User user = createValidUser();
        user.setBornDate(LocalDate.now().minusYears(10)); // Menor de edad
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);

        MultipleUserValidationExceptions exception = assertThrows(
                MultipleUserValidationExceptions.class,
                () -> registerUseCase.registerOwner(user)
        );

        assertTrue(exception.getErrors().contains(UserValidationMessages.USER_UNDERAGE));
    }

    @Test
    void validateRoleOwner_ShouldReturnTrueForOwner() {
        Long userId = 1L;
        User user = new User();
        user.setRole(UserUtils.ROLE_OWNER_ID);
        when(userPersistencePort.findById(userId)).thenReturn(user);

        boolean result = registerUseCase.validateRoleOwner(userId);

        assertTrue(result);
    }

    @Test
    void validateRoleOwner_ShouldReturnFalseForNonOwner() {
        Long userId = 1L;
        User user = new User();
        user.setRole(UserUtils.ROLE_CUSTOMER_ID);
        when(userPersistencePort.findById(userId)).thenReturn(user);

        boolean result = registerUseCase.validateRoleOwner(userId);

        assertFalse(result);
    }

    @Test
    void getPhoneNumber_ShouldReturnUserPhone() {
        Long userId = 1L;
        User user = new User();
        user.setPhone("123456789");
        when(userPersistencePort.findById(userId)).thenReturn(user);

        String phone = registerUseCase.getPhoneNumber(userId);

        assertEquals("123456789", phone);
    }

    @Test
    void getEmail_ShouldReturnUserEmail() {
        Long userId = 1L;
        User user = new User();
        user.setEmail("test@example.com");
        when(userPersistencePort.findById(userId)).thenReturn(user);

        String email = registerUseCase.getEmail(userId);

        assertEquals("test@example.com", email);
    }

    private User createValidUser() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setPassword("securePassword123");
        user.setIdentificationnumber("123456789");
        user.setPhone("123456789");
        user.setBornDate(LocalDate.now().minusYears(20)); // Mayor de edad
        return user;
    }
}