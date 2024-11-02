package foot_court.users.domain.api.usecase;

import foot_court.users.domain.api.IRegisterServicePort;
import foot_court.users.domain.model.User;
import foot_court.users.domain.spi.IEncryptPasswordPort;
import foot_court.users.domain.spi.IUserPersistencePort;
import foot_court.users.domain.utils.UserValidationMessages;
import foot_court.users.domain.utils.UserUtils;
import foot_court.users.domain.exception.MultipleUserValidationExceptions;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class RegisterUseCase implements IRegisterServicePort {

    private final IEncryptPasswordPort encryptPasswordPort;
    private final IUserPersistencePort userPersistencePort;

    public RegisterUseCase(IEncryptPasswordPort encryptPasswordPort, IUserPersistencePort userPersistencePort) {
        this.encryptPasswordPort = encryptPasswordPort;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void registerOwner(User user) {
        registerUser(user);
        setRoleOwner(user);
        userPersistencePort.saveUser(user);
    }

    @Override
    public void registerEmployed(Long restaurantId, User user) {
        Long ownerId = userPersistencePort.getUserId();
        registerUser(user);
        setRoleEmployed(user);
        userPersistencePort.saveUserEmployee(ownerId, restaurantId, user);
    }

    @Override
    public void registerCustomer(User user) {
        registerUser(user);
        setRoleCustomer(user);
        userPersistencePort.saveUser(user);
    }

    @Override
    public Boolean validateRoleOwner(Long id) {
        User user = userPersistencePort.findById(id);
        Long roleId = user.getRole();
        return roleId.equals(UserUtils.ROLE_OWNER_ID);
    }

    @Override
    public String getPhoneNumber(Long userId) {
        User user = userPersistencePort.findById(userId);
        return user.getPhone();
    }

    private void registerUser(User user) {
        validateInfo(user);
        encryptPassword(user);
    }

    private void validateInfo(User user) {
        List<String> errors = new ArrayList<>();
        if (userPersistencePort.existsByEmail(user.getEmail())) {
            errors.add(UserValidationMessages.USER_ALREADY_EXISTS);
        }
        if (user.getEmail() == null || !user.getEmail().matches(UserUtils.EMAIL_REGEX)) {
            errors.add(UserValidationMessages.INVALID_EMAIL_FORMAT);
        }
        if (user.getIdentificationnumber() == null || !user.getIdentificationnumber().matches(UserUtils.ID_DOCUMENT_REGEX)) {
            errors.add(UserValidationMessages.INVALID_ID_DOCUMENT);
        }
        if (user.getPhone() == null || user.getPhone().length() > UserUtils.MAX_PHONE_NUMBER_LENGTH) {
            errors.add(UserValidationMessages.CELLULAR_LENGTH_EXCEEDED);
        }
        if (user.getBornDate() == null || Period.between(user.getBornDate(), LocalDate.now()).getYears() < UserUtils.MIN_AGE) {
            errors.add(UserValidationMessages.USER_UNDERAGE);
        }
        if (!errors.isEmpty()) {
            throw new MultipleUserValidationExceptions(errors);
        }
    }

    private void encryptPassword(User user) {
        String encryptedPassword = encryptPasswordPort.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    private void setRoleOwner(User user) {
        user.setRole(UserUtils.ROLE_OWNER_ID);
    }

    private void setRoleEmployed(User user) {
        user.setRole(UserUtils.ROLE_EMPLOYED_ID);
    }

    private void setRoleCustomer(User user) {
        user.setRole(UserUtils.ROLE_CUSTOMER_ID);
    }
}
