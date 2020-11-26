package net.codejava.swing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate email typed in the email text field.
 * 
 * @author Bruce Tieu
 *
 */
public class EmailValidator {
    private Pattern _pattern;
    private Matcher _matcher;

    // Common email regex.
    private static final String _EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Compile the email regular expression.
     */
    public EmailValidator() {
        _pattern = Pattern.compile(_EMAIL_PATTERN);
    }

    /**
     * Validate email with the regex.
     * 
     * @param email The email passed in.
     */
    public boolean validate(final String email) {
        _matcher = _pattern.matcher(email);
        return _matcher.matches();

    }
}
