package foot_court.users.ports.persistency.mysql.adapter;

import foot_court.users.domain.model.User;
import foot_court.users.domain.spi.IUserPersistencePort;
import foot_court.users.ports.persistency.mysql.entity.UserEntity;
import foot_court.users.ports.persistency.mysql.mapper.UserEntityMapper;
import foot_court.users.ports.persistency.mysql.repository.UserRepository;

public class UserPersistenceAdapter implements IUserPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userMapper;

    public UserPersistenceAdapter(UserRepository userRepository, UserEntityMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
