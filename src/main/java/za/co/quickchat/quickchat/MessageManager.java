package za.co.quickchat.quickchat;

import java.util.ArrayList;

/**
 * Keeps track of every message entered in QuickChat using arrays, and lets the
 * user search, delete and report on those messages.
 *
 * The messages, messageIDs and messageHashes arrays are parallel arrays: the
 * same index in each one refers to the same message. They hold every message
 * that was sent or stored. Disregarded messages are deleted by the user, so
 * they are only kept in the disregarded messages array.
 *
 * @author Praise Nemberi ST10542243
 */
public class MessageManager {

    public static final int SEND = 1;
    public static final int DISREGARD = 2;
    public static final int STORE = 3;

    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private final ArrayList<Message> storedMessages = new ArrayList<>();

    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();

    /**
     * Adds a message to the correct array based on what the user chose to do
     * with it.
     *
     * @param message the message the user entered
     * @param choice 1 for sent, 2 for disregarded, 3 for stored
     */
    public void addMessage(Message message, int choice) {
        switch (choice) {
            case SEND:
                sentMessages.add(message);
                break;
            case DISREGARD:
                disregardedMessages.add(message);
                return;
            case STORE:
                storedMessages.add(message);
                break;
            default:
                return;
        }

        messages.add(message);
        messageIDs.add(message.getMessageID());
        messageHashes.add(message.createMessageHash());
    }

    /**
     * Lists the sender and recipient of every stored message.
     *
     * @param sender the name of the logged-in user who wrote the messages
     * @return one line per stored message, or a note that there are none
     */
    public String displaySendersAndRecipients(String sender) {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }

        StringBuilder result = new StringBuilder();
        for (Message message : storedMessages) {
            result.append("Sender: ").append(sender)
                    .append(" | Recipient: ").append(message.getRecipient())
                    .append("\n");
        }
        return result.toString().trim();
    }

    /**
     * Finds the longest stored message.
     *
     * @return the text of the longest stored message, or a note that there are
     * none
     */
    public String getLongestMessage() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }

        Message longest = storedMessages.get(0);
        for (Message message : storedMessages) {
            if (message.getMessageText().length() > longest.getMessageText().length()) {
                longest = message;
            }
        }
        return longest.getMessageText();
    }

    /**
     * Returns the text of every sent message.
     *
     * @return an array of sent message texts
     */
    public String[] getSentMessages() {
        return toTextArray(sentMessages);
    }

    /**
     * Reads the messages saved in the JSON file into the stored messages array.
     *
     * @return a message saying how many stored messages were loaded
     */
    public String loadStoredMessages() {
        ArrayList<Message> fileMessages = Message.loadStoredMessages();

        for (Message message : fileMessages) {
            addMessage(message, STORE);
        }
        return fileMessages.size() + " stored message(s) loaded.";
    }

    /**
     * Returns the text of every disregarded message.
     *
     * @return an array of disregarded message texts
     */
    public String[] getDisregardedMessages() {
        return toTextArray(disregardedMessages);
    }

    /**
     * Returns the stored messages so that they can be saved back to the JSON
     * file.
     *
     * @return the stored messages
     */
    public ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    /**
     * Returns the hash of every sent and stored message.
     *
     * @return an array of message hashes
     */
    public String[] getMessageHashes() {
        return messageHashes.toArray(new String[0]);
    }

    /**
     * Returns the ID of every sent and stored message.
     *
     * @return an array of message IDs
     */
    public String[] getMessageIDs() {
        return messageIDs.toArray(new String[0]);
    }

    /**
     * Searches the message ID array and shows the matching recipient and
     * message.
     *
     * @param messageID the ID to search for
     * @return the recipient and message, or a note that the ID was not found
     */
    public String searchByMessageID(String messageID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(messageID)) {
                Message found = messages.get(i);
                return "Recipient: " + found.getRecipient()
                        + "\nMessage: " + found.getMessageText();
            }
        }
        return "Message ID " + messageID + " was not found.";
    }

    /**
     * Searches for every sent or stored message going to one recipient.
     *
     * @param recipient the recipient's cell number
     * @return each matching message on its own line, or a note that none were
     * found
     */
    public String searchByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();

        for (Message message : messages) {
            if (message.getRecipient().equals(recipient)) {
                result.append(message.getMessageText()).append("\n");
            }
        }

        if (result.length() == 0) {
            return "No messages found for " + recipient + ".";
        }
        return result.toString().trim();
    }

    /**
     * Deletes a message by searching the message hash array. The message is
     * removed from the parallel arrays and from the sent or stored array.
     *
     * @param messageHash the hash of the message to delete
     * @return a message confirming the deletion, or a note that the hash was
     * not found
     */
    public String deleteByMessageHash(String messageHash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(messageHash)) {
                Message removed = messages.remove(i);
                messageIDs.remove(i);
                messageHashes.remove(i);
                sentMessages.remove(removed);
                storedMessages.remove(removed);

                return "Message: \"" + removed.getMessageText()
                        + "\" successfully deleted.";
            }
        }
        return "Message hash " + messageHash + " was not found.";
    }

    /**
     * Copies the text of each message in a list into a String array.
     *
     * @param list the messages to copy
     * @return an array holding the text of each message
     */
    private String[] toTextArray(ArrayList<Message> list) {
        String[] texts = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            texts[i] = list.get(i).getMessageText();
        }
        return texts;
    }
}
