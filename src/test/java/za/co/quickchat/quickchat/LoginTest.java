package za.co.quickchat.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Login class, using the test data specified in the
 * PROG5121 Portfolio of Evidence brief.
 *
 * @author Praise Nemberi ST10542243
 */
public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testRegisterUserSuccessful() {
        Login login = new Login();
        assertEquals("User registered successfully.",
                login.registerUser("Praise", "Nemberi", "kyl_1",
                        "Ch&&sec@ke99!", "+27838968976"));
    }

    @Test
    public void testRegisterUserRejectsBadUsername() {
        Login login = new Login();
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                login.registerUser("Praise", "Nemberi", "kyle!!!!!!!",
                        "Ch&&sec@ke99!", "+27838968976"));
    }

    @Test
    public void testRegisterUserRejectsBadPassword() {
        Login login = new Login();
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                login.registerUser("Praise", "Nemberi", "kyl_1",
                        "password", "+27838968976"));
    }

    @Test
    public void testRegisterUserRejectsBadCellNumber() {
        Login login = new Login();
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.",
                login.registerUser("Praise", "Nemberi", "kyl_1",
                        "Ch&&sec@ke99!", "08966553"));
    }

    @Test
    public void testLoginSuccessful() {
        Login login = new Login();
        login.registerUser("Praise", "Nemberi", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login();
        login.registerUser("Praise", "Nemberi", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    @Test
    public void testReturnLoginStatusSuccessful() {
        Login login = new Login();
        login.registerUser("Praise", "Nemberi", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Welcome Praise, Nemberi it is great to see you.",
                login.returnLoginStatus(true));
    }

    @Test
    public void testReturnLoginStatusFailed() {
        Login login = new Login();
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus(false));
    }
}