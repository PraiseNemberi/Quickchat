package za.co.quickchat.quickchat;

/**
 * Handles user registration and login for the QuickChat application.
 *
 * @author Praise Nemberi ST10542243
 */
public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;

    /**
     * Checks that a username contains an underscore and is no more than five
     * characters in length.
     *
     * @param username the username to validate
     * @return true if the username is correctly formatted, false otherwise
     */
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks that a password meets the required complexity rules: at least
     * eight characters, containing a capital letter, a number and a special
     * character.
     *
     * @param password the password to validate
     * @return true if the password meets all complexity rules, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char currentCharacter = password.charAt(i);

            if (Character.isUpperCase(currentCharacter)) {
                hasCapital = true;
            } else if (Character.isDigit(currentCharacter)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(currentCharacter)) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks that a cell phone number contains the South African international
     * dialling code followed by nine digits.
     *
     * Regular expression syntax adapted from: Oracle, 2023. Class Pattern.
     * [Online] Available at:
     * https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/regex/Pattern.html
     * [Accessed 13 September 2026].
     *
     * @param cellNumber the cell phone number to validate
     * @return true if the number is correctly formatted, false otherwise
     */
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.matches("^\\+27[0-9]{9}$");
    }

    /**
     * Registers a user if the username, password and cell phone number all meet
     * the required formats, and returns the appropriate message.
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param username the chosen username
     * @param password the chosen password
     * @param cellNumber the user's cell phone number
     * @return a message describing the outcome of the registration
     */
    public String registerUser(String firstName, String lastName,
            String username, String password, String cellNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredUsername = username;
        this.registeredPassword = password;

        return "User registered successfully.";
    }
}
