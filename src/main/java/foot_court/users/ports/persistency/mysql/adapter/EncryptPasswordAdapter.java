package foot_court.users.ports.persistency.mysql.adapter;

import foot_court.users.domain.spi.IEncryptPasswordPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptPasswordAdapter implements IEncryptPasswordPort {

    private final BCryptPasswordEncoder encoder;

    public EncryptPasswordAdapter() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String password) {
        return encoder.encode(password);
    }
}
