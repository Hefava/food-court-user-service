package foot_court.users.domain.utils;

public class UserValidationMessages {

  private UserValidationMessages() {
    throw new AssertionError("Cannot instantiate this class");
  }

  public static final String ERRORS = "errors";
  public static final String INVALID_EMAIL_FORMAT = "Mail does not have a valid format";
  public static final String INVALID_ID_DOCUMENT = "Identity document must be numeric";
  public static final String CELLULAR_LENGTH_EXCEEDED = "Phone number must not exceed 13 characters";
  public static final String USER_UNDERAGE = "User must be of legal age";
  public static final String USER_ALREADY_EXISTS = "A user already exists with the provided email";
}
