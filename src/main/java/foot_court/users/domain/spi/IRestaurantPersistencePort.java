package foot_court.users.domain.spi;

public interface IRestaurantPersistencePort {
    void enterEmployee(Long ownerId, Long restaurantId, Long employeeId);
}
