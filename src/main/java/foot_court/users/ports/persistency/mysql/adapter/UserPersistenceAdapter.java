package foot_court.users.ports.persistency.mysql.adapter;

import foot_court.users.domain.model.User;
import foot_court.users.domain.spi.IUserPersistencePort;
import foot_court.users.ports.persistency.mysql.entity.UserEntity;
import foot_court.users.ports.persistency.mysql.mapper.UserEntityMapper;
import foot_court.users.ports.persistency.mysql.repository.IRestaurantFeign;
import foot_court.users.ports.persistency.mysql.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userMapper;
    private final IRestaurantFeign restaurantFeign;

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userRepository.save(userEntity);
    }
    @Override
    @Transactional
    public void saveUserEmployee(Long ownerId, Long restaurantId, User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userRepository.save(userEntity);
        restaurantFeign.enterEmployee(ownerId, restaurantId, userEntity.getId());
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return userMapper.toUser(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Long getUserId() {
        UserDetails userId = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(userId.getUsername());
    }
}
