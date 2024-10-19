package foot_court.users.domain.spi;

public interface IEncryptPasswordPort {
    String encrypt(String password);
}