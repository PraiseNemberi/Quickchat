
package za.co.quickchat.quickchat;

/**
 * Handles user registration and login for the QuickChat application.
 *
 * @author Praise Nemberi ST10542243
 */
public class Login {

    /**
     * Checks that a username contains an underscore and is no more than
     * five characters in length.
     *
     * @param username the username to validate
     * @return true if the username is correctly formatted, false otherwise
     */
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
        /**
     * Checks that a password meets the required complexity rules: at least
     * eight characters, containing a capital letter, a number and a
     * special character.
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
}
