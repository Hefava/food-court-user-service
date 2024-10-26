package foot_court.users.domain.spi;

import foot_court.users.domain.model.User;

public interface IUserPersistencePort {
    void saveUser(User user);
    User findById(Long id);
    boolean existsByEmail(String email);
}
