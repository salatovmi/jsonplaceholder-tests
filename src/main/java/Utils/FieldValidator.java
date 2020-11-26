package Utils;

import org.apache.commons.validator.routines.EmailValidator;

public class FieldValidator {
    //Validates that string contains valid email address.
    public static boolean isEmailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
