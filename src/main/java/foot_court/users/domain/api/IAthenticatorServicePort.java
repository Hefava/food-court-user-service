package foot_court.users.domain.api;

import foot_court.users.domain.utils.Validation;

public interface IAthenticatorServicePort {
    String login(String email, String password);
    Validation validateToken(String token);
}
