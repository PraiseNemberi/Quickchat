package za.co.quickchat.quickchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a single message in the QuickChat application and validates the
 * message's ID and recipient.
 *
 * @author Praise Nemberi ST10542243
 */
public class Message {

    private static final int MAX_MESSAGE_LENGTH = 250;
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final String STORAGE_FILE = "messages.json";
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

    /**
     * Handles the user's choice for what to do with the message: send it,
     * disregard it or store it. Sent messages are added to the list of sent
     * messages.
     *
     * @param choice 1 to send, 2 to disregard, 3 to store
     * @return the message confirming what happened to the message
     */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid option; please choose 1, 2 or 3.";
        }
    }

    /**
     * Returns the full details of this message in the order: message ID,
     * message hash, recipient, message.
     *
     * @return the message details
     */
    public String getMessageDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    /**
     * Returns the details of every message sent while the program is running.
     *
     * @return all sent messages, or a note that none have been sent
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }

        StringBuilder allMessages = new StringBuilder();
        for (Message message : sentMessages) {
            allMessages.append(message.getMessageDetails()).append("\n\n");
        }
        return allMessages.toString().trim();
    }

    /**
     * Returns the total number of messages sent while the program is running.
     *
     * @return the number of sent messages
     */
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    /**
     * Empties the list of sent messages so that each unit test starts fresh.
     */
    public static void clearSentMessages() {
        sentMessages.clear();
    }

    /**
     * Stores this message in a JSON file, keeping any messages that were stored
     * earlier.
     *
     * JSON storage approach adapted from the Gson user guide [1].
     *
     * [1] Google, "Gson user guide," GitHub. Accessed: Oct. 4, 2026. [Online].
     * Available: https://github.com/google/gson/blob/main/UserGuide.md
     *
     * @return a message confirming the message was stored, or an error message
     * if it could not be stored
     */
    public String storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Message> storedMessages = readStoredMessages(gson);
        storedMessages.add(this);

        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            gson.toJson(storedMessages, writer);
            return "Message successfully stored.";
        } catch (IOException e) {
            return "Message could not be stored: " + e.getMessage();
        }
    }

    /**
     * Reads the messages already saved in the JSON file.
     *
     * JSON reading approach adapted from the Gson user guide [1].
     *
     * @param gson the Gson object used to read the file
     * @return the stored messages, or an empty list if there are none yet
     */
    private static ArrayList<Message> readStoredMessages(Gson gson) {
        Type listType = new TypeToken<ArrayList<Message>>() {
        }.getType();

        try (FileReader reader = new FileReader(STORAGE_FILE)) {
            ArrayList<Message> existingMessages = gson.fromJson(reader, listType);
            if (existingMessages != null) {
                return existingMessages;
            }
        } catch (IOException e) {
            // No file yet, so there are no stored messages to keep.
        }
        return new ArrayList<>();
    }
}
