package foot_court.users.domain.utils;

public class UserUtils {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static final String ID_DOCUMENT_REGEX = "\\d+";

    public static final int MIN_AGE = 18;

    public static final Long ROLE_OWNER_ID = 2L;

    public static final int MAX_PHONE_NUMBER_LENGTH = 13;

    private UserUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}