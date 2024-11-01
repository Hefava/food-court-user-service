package foot_court.users.ports.persistency.mysql.adapter;

import foot_court.users.domain.spi.IRestaurantPersistencePort;
import foot_court.users.ports.persistency.mysql.repository.IRestaurantFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IRestaurantFeignAdapter implements IRestaurantPersistencePort {
    private final IRestaurantFeign restaurantFeign;

    @Override
    public void enterEmployee(Long ownerId, Long restaurantId, Long employeeId) {
        restaurantFeign.enterEmployee(ownerId, restaurantId, employeeId);
    }
}