package foot_court.users.domain.api.usecase;

import foot_court.users.domain.api.IAthenticatorServicePort;
import foot_court.users.domain.model.User;
import foot_court.users.domain.spi.IAuthenticationPersistencePort;
import foot_court.users.domain.utils.Validation;

public class AuthenticatorUseCase implements IAthenticatorServicePort {
    private final IAuthenticationPersistencePort authenticationPort;

    public AuthenticatorUseCase(IAuthenticationPersistencePort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @Override
    public String login(String email, String password) {
        User user = authenticationPort.authenticate(email, password);
        return authenticationPort.generateToken(user);
    }

    @Override
    public Validation validateToken(String token) {
        return authenticationPort.validateToken(token);
    }
}
