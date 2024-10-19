package foot_court.users.ports.application.http.controller;

import foot_court.users.domain.api.IRegisterServicePort;
import foot_court.users.domain.model.User;
import foot_court.users.ports.application.http.dto.RegisterUserRequest;
import foot_court.users.ports.application.http.mapper.RegisterUserRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class RegisterController {

    private final IRegisterServicePort registerServicePort;
    private final RegisterUserRequestMapper registerUserRequestMapper;

    @Operation(summary = "Register new owner", description = "Register a new owner in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Wrong request", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/register-owner")
    public ResponseEntity<Void> registerOwner(
            @RequestBody @Parameter(required = true) RegisterUserRequest request) {
        User user = registerUserRequestMapper.toDomain(request);
        registerServicePort.registerOwner(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}