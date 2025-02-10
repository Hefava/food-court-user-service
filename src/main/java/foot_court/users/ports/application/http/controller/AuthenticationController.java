package foot_court.users.ports.application.http.controller;

import foot_court.users.domain.api.IAthenticatorServicePort;
import foot_court.users.ports.application.http.dto.LoginRequest;
import foot_court.users.ports.application.http.dto.LoginResponse;
import foot_court.users.ports.application.http.dto.ValidationResponse;
import foot_court.users.ports.application.http.mapper.ValidationResponseMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class AuthenticationController {
    private final IAthenticatorServicePort athenticatorServicePort;
    private final ValidationResponseMapper validationResponseMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Parameter(required = true) LoginRequest loginRequest) {
        String token = athenticatorServicePort.login(loginRequest.getEmail(), loginRequest.getPassword());
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-token/{token}")
    public ResponseEntity<ValidationResponse> validateToken(@PathVariable @Parameter(required = true) String token) {
        ValidationResponse validationResponse = validationResponseMapper.toDto(athenticatorServicePort.validateToken(token));
        return ResponseEntity.ok(validationResponse);
    }
}