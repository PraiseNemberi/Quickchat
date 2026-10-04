package za.co.quickchat.quickchat;

import java.util.Random;

/**
 * Represents a single message in the QuickChat application and validates the
 * message's ID and recipient.
 *
 * @author Praise Nemberi ST10542243
 */
public class Message {

    private static final int MAX_MESSAGE_LENGTH = 250;
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;

    /**
     * Creates a new message.
     *
     * @param messageID the unique ten-digit ID of the message
     * @param messageNumber the number of the message (starts at 0)
     * @param recipient the recipient's cell phone number
     * @param messageText the text of the message
     */
    public Message(String messageID, int messageNumber, String recipient,
            String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    /**
     * Checks that the message ID is no more than ten characters long.
     *
     * @return true if the message ID is valid, false otherwise
     */
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    /**
     * Checks that the recipient's cell number contains the international code
     * and is correctly formatted, by reusing the Login class check.
     *
     * @return a message describing whether the number was captured
     */
    public String checkRecipientCell() {
        Login login = new Login();

        if (login.checkCellPhoneNumber(recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    /**
     * Generates a random ten-digit message ID. Each digit is generated
     * separately so that IDs can start with zero.
     *
     * @return a ten-digit message ID
     */
    public static String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    /**
     * Returns the message confirming that the message ID was generated.
     *
     * @return the message ID confirmation text
     */
    public String displayMessageID() {
        return "Message ID generated: " + messageID;
    }

    /**
     * Creates the message hash: the first two digits of the message ID, the
     * message number, then the first and last words of the message, all in
     * capitals. For example 00:0:HITONIGHT.
     *
     * @return the message hash
     */
    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = stripPunctuation(words[0]);
        String lastWord = stripPunctuation(words[words.length - 1]);

        return (firstTwoDigits + ":" + messageNumber + ":" + firstWord
                + lastWord).toUpperCase();
    }

    /**
     * Removes punctuation from a word so that "tonight?" becomes "tonight".
     *
     * @param word the word to clean
     * @return the word containing only letters and digits
     */
    private String stripPunctuation(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    /**
     * Checks that the message is no more than 250 characters long.
     *
     * @return a message saying the message is ready to send, or by how many
     * characters it is too long
     */
    public String checkMessageLength() {
        int length = messageText.length();

        if (length <= MAX_MESSAGE_LENGTH) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by "
                + (length - MAX_MESSAGE_LENGTH) + "; please reduce the size.";
    }
}
