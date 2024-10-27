package foot_court.users.domain.spi;

import foot_court.users.domain.model.User;
import foot_court.users.domain.utils.Validation;

public interface IAuthenticationPersistencePort {
    User authenticate(String email, String password);
    String generateToken(User user);
    Validation validateToken(String token);
}
