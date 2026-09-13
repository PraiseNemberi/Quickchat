
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
}
