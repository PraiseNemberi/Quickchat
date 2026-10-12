package za.co.quickchat.quickchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    public void testMessageHashForFirstMessage() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    public void testMessageHashesInLoop() {
        String[] ids = {"0012345678", "0812345678"};
        String[] texts = {MIKE_TEXT, "Hi Keegan, did you receive the payment?"};
        String[] expectedHashes = {"00:0:HITONIGHT", "08:1:HIPAYMENT"};

        for (int i = 0; i < ids.length; i++) {
            Message message = new Message(ids[i], i, MIKE_NUMBER, texts[i]);
            assertEquals(expectedHashes[i], message.createMessageHash());
        }
    }

    @Test
    public void testGeneratedMessageIDIsTenDigits() {
        for (int i = 0; i < 20; i++) {
            String id = Message.generateMessageID();
            assertTrue(id.matches("[0-9]{10}"));
            assertTrue(new Message(id, 0, MIKE_NUMBER, MIKE_TEXT).checkMessageID());
        }
    }

    @Test
    public void testMessageIDTooLongFails() {
        Message message = new Message("00123456789", 0, MIKE_NUMBER, MIKE_TEXT);
        assertFalse(message.checkMessageID());
    }

    @Test
    public void testMessageIDCreated() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("Message ID generated: 0012345678", message.displayMessageID());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("Message successfully sent.", message.sentMessage(1));
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message("0812345678", 1, BAD_NUMBER,
                "Hi Keegan, did you receive the payment?");
        assertEquals("Press 0 to delete the message.", message.sentMessage(2));
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }

    @Test
    public void testInvalidSendOption() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("Invalid option; please choose 1, 2 or 3.", message.sentMessage(9));
    }

    @Test
    public void testTotalMessagesCountsOnlySentMessages() {
        Message sent = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        Message discarded = new Message("0812345678", 1, MIKE_NUMBER,
                "Hi Keegan, did you receive the payment?");

        sent.sentMessage(1);
        discarded.sentMessage(2);

        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testNoMessagesSentYet() {
        assertEquals(0, Message.returnTotalMessages());
        assertEquals("No messages have been sent.", Message.printMessages());
    }

    @Test
    public void testPrintMessagesShowsDetailsInOrder() {
        Message message = new Message(TEST_ID, 0, MIKE_NUMBER, MIKE_TEXT);
        message.sentMessage(1);

        assertEquals("""
                     Message ID: 0012345678
                     Message Hash: 00:0:HITONIGHT
                     Recipient: +27718693002
                     Message: Hi Mike, can you join us for dinner tonight?""",
                Message.printMessages());
    }
}
