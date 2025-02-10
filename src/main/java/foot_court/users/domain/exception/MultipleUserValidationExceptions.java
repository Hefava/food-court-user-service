package foot_court.users.domain.exception;
import java.util.List;

public class MultipleUserValidationExceptions extends RuntimeException {

    private final List<String> errors;

    public MultipleUserValidationExceptions(List<String> errors) {
        super("Multiple validation errors occurred");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
