package foot_court.users.infrastructure.exceptionhandler;

import foot_court.users.domain.exception.MultipleUserValidationExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "errors";

    @ExceptionHandler(MultipleUserValidationExceptions.class)
    public ResponseEntity<Map<String, List<String>>> handleMultipleUserValidationExceptions(
            MultipleUserValidationExceptions multipleUserValidationExceptions) {
        Map<String, List<String>> response = new HashMap<>();
        response.put(MESSAGE, multipleUserValidationExceptions.getErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
