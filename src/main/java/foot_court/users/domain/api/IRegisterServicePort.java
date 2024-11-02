package foot_court.users.domain.api;

import foot_court.users.domain.model.User;

public interface IRegisterServicePort {
    void registerOwner(User user);
    void registerEmployed(Long restaurantId, User user);
    void registerCustomer(User user);
    Boolean validateRoleOwner(Long id);
    String getPhoneNumber(Long id);
}
