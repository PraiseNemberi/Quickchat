package za.co.quickchat.quickchat;

/**
 * Represents a single message in the QuickChat application and validates the
 * message's ID and recipient.
 *
 * @author Praise Nemberi ST10542243
 */
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;

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
     * Checks that the recipient's cell number contains the international
     * code and is correctly formatted, by reusing the Login class check.
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
}
