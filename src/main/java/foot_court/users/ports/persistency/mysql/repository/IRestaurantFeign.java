package foot_court.users.ports.persistency.mysql.repository;

import foot_court.users.ports.feign.FeingClientConfiguration;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "place-service", url = "http://localhost:8083", configuration = FeingClientConfiguration.class)
public interface IRestaurantFeign {
    @PostMapping("/restaurants/enter-employee")
    Void enterEmployee(@RequestParam @Parameter Long ownerId,
                       @RequestParam @Parameter Long restaurantId,
                       @RequestParam @Parameter Long employeeId);
}
