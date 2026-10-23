package za.co.quickchat.quickchat;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the MessageManager class, using the Part 3 test data from the
 * brief.
 *
 * @author Praise Nemberi ST10542243
 */
public class MessageManagerTest {

    private static final String[] IDS = {
        "0012345678", "1112345678", "2212345678", "0838884567", "4412345678"
    };
    private static final String[] RECIPIENTS = {
        "+27834557896", "+27838884567", "+27834484567", "0838884567", "+27838884567"
    };
    private static final String[] TEXTS = {
        "Did you get the cake?",
        "Where are you? You are late! I have asked you to be on time.",
        "Yohoooo, I am at your gate.",
        "It is dinner time!",
        "Ok, I am leaving without you."
    };
    private static final int[] CHOICES = {
        MessageManager.SEND, MessageManager.STORE, MessageManager.DISREGARD,
        MessageManager.SEND, MessageManager.STORE
    };

    private MessageManager manager;

    /**
     * Populates the arrays with the five test messages before every test.
     */
    @BeforeEach
    public void setUp() {
        manager = new MessageManager();

        for (int i = 0; i < IDS.length; i++) {
            Message message = new Message(IDS[i], i, RECIPIENTS[i], TEXTS[i]);
            manager.addMessage(message, CHOICES[i]);
        }
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        String[] expected = {"Did you get the cake?", "It is dinner time!"};
        assertArrayEquals(expected, manager.getSentMessages());
    }

    @Test
    public void testDisregardedMessagesArrayCorrectlyPopulated() {
        String[] expected = {"Yohoooo, I am at your gate."};
        assertArrayEquals(expected, manager.getDisregardedMessages());
    }

    @Test
    public void testStoredMessagesArrayCorrectlyPopulated() {
        assertEquals(2, manager.getStoredMessages().size());
        assertEquals(TEXTS[1], manager.getStoredMessages().get(0).getMessageText());
        assertEquals(TEXTS[4], manager.getStoredMessages().get(1).getMessageText());
    }

    @Test
    public void testMessageHashArrayCorrectlyPopulated() {
        String[] expected = {"00:0:DIDCAKE", "11:1:WHERETIME", "08:3:ITTIME", "44:4:OKYOU"};
        assertArrayEquals(expected, manager.getMessageHashes());
    }

    @Test
    public void testMessageIDArrayCorrectlyPopulated() {
        String[] expected = {"0012345678", "1112345678", "0838884567", "4412345678"};
        assertArrayEquals(expected, manager.getMessageIDs());
    }

    @Test
    public void testDisplayLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                manager.getLongestMessage());
    }

    @Test
    public void testDisplaySendersAndRecipients() {
        assertEquals("Sender: Kyle Smith | Recipient: +27838884567\n"
                + "Sender: Kyle Smith | Recipient: +27838884567",
                manager.displaySendersAndRecipients("Kyle Smith"));
    }

    @Test
    public void testSearchForMessageID() {
        assertEquals("Recipient: 0838884567\nMessage: It is dinner time!",
                manager.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchForMessageIDNotFound() {
        assertEquals("Message ID 9999999999 was not found.",
                manager.searchByMessageID("9999999999"));
    }

    @Test
    public void testSearchMessagesForRecipient() {
        assertEquals("Where are you? You are late! I have asked you to be on time.\n"
                + "Ok, I am leaving without you.",
                manager.searchByRecipient("+27838884567"));
    }

    @Test
    public void testSearchMessagesForRecipientNotFound() {
        assertEquals("No messages found for +27000000000.",
                manager.searchByRecipient("+27000000000"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                manager.deleteByMessageHash("11:1:WHERETIME"));

        assertArrayEquals(new String[]{"00:0:DIDCAKE", "08:3:ITTIME", "44:4:OKYOU"},
                manager.getMessageHashes());
        assertEquals(1, manager.getStoredMessages().size());
    }

    @Test
    public void testDeleteMessageHashNotFound() {
        assertEquals("Message hash 99:9:NOTHERE was not found.",
                manager.deleteByMessageHash("99:9:NOTHERE"));
        assertEquals(4, manager.getMessageHashes().length);
    }

    @Test
    public void testDisplayReport() {
        assertEquals("""
                     QuickChat Message Report

                     Message ID: 0012345678
                     Message Hash: 00:0:DIDCAKE
                     Recipient: +27834557896
                     Message: Did you get the cake?

                     Message ID: 1112345678
                     Message Hash: 11:1:WHERETIME
                     Recipient: +27838884567
                     Message: Where are you? You are late! I have asked you to be on time.

                     Message ID: 0838884567
                     Message Hash: 08:3:ITTIME
                     Recipient: 0838884567
                     Message: It is dinner time!

                     Message ID: 4412345678
                     Message Hash: 44:4:OKYOU
                     Recipient: +27838884567
                     Message: Ok, I am leaving without you.""",
                manager.displayReport());
    }

    @Test
    public void testEmptyManagerMessages() {
        MessageManager empty = new MessageManager();
        assertEquals("There are no stored messages.", empty.getLongestMessage());
        assertEquals("There are no messages to report.", empty.displayReport());
    }
}
