package foot_court.users.infrastructure.configuration;

import foot_court.users.domain.api.IAthenticatorServicePort;
import foot_court.users.domain.api.IRegisterServicePort;
import foot_court.users.domain.api.usecase.AuthenticatorUseCase;
import foot_court.users.domain.api.usecase.RegisterUseCase;
import foot_court.users.domain.spi.IAuthenticationPersistencePort;
import foot_court.users.domain.spi.IEncryptPasswordPort;
import foot_court.users.domain.spi.IUserPersistencePort;
import foot_court.users.ports.persistency.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    private final UserRepository userRepository;

    @Autowired
    public BeanConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public IRegisterServicePort userServicePort(
            IEncryptPasswordPort encryptPasswordPort,
            IUserPersistencePort userPersistencePort) {
        return new RegisterUseCase(encryptPasswordPort, userPersistencePort);
    }

    @Bean
    public IAthenticatorServicePort authenticatorServicePort(
            IAuthenticationPersistencePort authenticationPort) {
        return new AuthenticatorUseCase(authenticationPort);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            try {
                Long userId = Long.valueOf(username);
                return userRepository.findById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            } catch (NumberFormatException e) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            }
        };
    }
}