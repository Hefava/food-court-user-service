package foot_court.users.ports.persistency.mysql.repository;

import foot_court.users.ports.persistency.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findById(Long userID);
    Optional<UserEntity> findByEmail(String email);
}
