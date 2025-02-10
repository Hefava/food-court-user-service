package foot_court.users.ports.persistency.mysql.adapter;

import foot_court.users.domain.exception.InvalidCredentialsException;
import foot_court.users.domain.model.User;
import foot_court.users.domain.spi.IAuthenticationPersistencePort;
import foot_court.users.domain.utils.Validation;
import foot_court.users.ports.persistency.mysql.entity.UserEntity;
import foot_court.users.ports.persistency.mysql.mapper.UserEntityMapper;
import foot_court.users.ports.persistency.mysql.repository.UserRepository;
import foot_court.users.ports.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static foot_court.users.domain.utils.UserUtils.INVALID_CREDENTIALS;
import static foot_court.users.domain.utils.UserUtils.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AthenticationAdapter implements IAuthenticationPersistencePort {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserEntityMapper userEntityMapper;
    private final JwtService jwtService;

    @Override
    public User authenticate(String email, String password) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException(INVALID_CREDENTIALS));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            return userEntityMapper.toUser(userEntity);

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS, e);
        }
    }

    @Override
    public String generateToken(User user) {
        return jwtService.getToken(userEntityMapper.toEntity(user));
    }

    @Override
    public Validation validateToken(String token) {
        String username = jwtService.extractUserId(token);
        Long userIdLong = Long.valueOf(username);
        UserEntity userEntity = userRepository.findById(userIdLong)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        String role = userEntity.getRole().getName().name();
        Boolean valid = jwtService.isTokenValid(token, userEntity);
        return new Validation(username, role, valid);
    }
}
