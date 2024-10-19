package foot_court.users.domain.api;

import foot_court.users.domain.model.User;

public interface IRegisterServicePort {
    void registerOwner(User user);
}
