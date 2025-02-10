package foot_court.users.ports.application.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserEmployeeRequest {
    private Long restaurantId;
    private String name;
    private String lastname;
    private String identificationnumber;
    private String phone;
    private LocalDate bornDate;
    private String email;
    private String password;
}
