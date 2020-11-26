package net.codejava.swing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate email typed in the email text field.
 * 
 * @author Bruce Tieu
 *
 */
public class Validator {
    private Pattern _emailPattern;
    private Pattern _phonePattern;
    private Matcher _emailMatcher;
    private Matcher _phoneMatcher;

    // Common email regex.
    private static final String _EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String _PHONE_PATTERN = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";

    /**
     * Compile the email regular expression.
     */
    public Validator() {
        _emailPattern = Pattern.compile(_EMAIL_PATTERN);
        _phonePattern = Pattern.compile(_PHONE_PATTERN);
    }

    /**
     * Validate email with the regex.
     * 
     * @param email The email passed in.
     * @return True, if the email matches the regex, false otherwise.
     */
    public boolean validateEmail(final String email) {
        _emailMatcher = _emailPattern.matcher(email);
        return _emailMatcher.matches();

    }

    /**
     * Validate the phone with regex.
     * 
     * @param phone The phone number passed in.
     * @return True, if the phone number matches the regex, false otherwise.
     */
    public boolean validatePhone(final String phone) {
        _phoneMatcher = _phonePattern.matcher(phone);
        return _phoneMatcher.matches();
    }
}
