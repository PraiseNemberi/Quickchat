package za.co.quickchat.quickchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Message class, using the test data from the brief.
 *
 * @author Praise Nemberi ST10542243
 */
public class MessageTest {

    private static final String TEST_ID = "0012345678";
    private static final String MIKE_NUMBER = "+27718693002";
    private static final String MIKE_TEXT = "Hi Mike, can you join us for dinner tonight?";
    private static final String BAD_NUMBER = "08575975889";
    private static final String BAD_NUMBER_MESSAGE = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

    /**
     * Empties the sent message list so every test starts fresh.
     */
    @BeforeEach
    public void setUp() {
        Message.clearSentMessages();
    }

    @Test
    public void testMessageLengthSuccess() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    public void testMessageLengthExactly250IsAllowed() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, "a".repeat(250));
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    public void testMessageLength251Fails() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, "a".repeat(251));
        assertEquals("Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength());
    }

    @Test
    public void testMessageLength260FailsByTen() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, "a".repeat(260));
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                message.checkMessageLength());
    }

    @Test
    public void testRecipientCorrectlyFormatted() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("Cell phone number successfully captured.",
                message.checkRecipientCell());
    }

    @Test
    public void testRecipientIncorrectlyFormatted() {
        Message message = new Message(TEST_ID, 1, BAD_NUMBER, MIKE_TEXT);
        assertEquals(BAD_NUMBER_MESSAGE, message.checkRecipientCell());
    }
}