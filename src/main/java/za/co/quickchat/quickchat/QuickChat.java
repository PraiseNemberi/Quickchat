package za.co.quickchat.quickchat;

import java.util.Scanner;

/**
 * Console application for the QuickChat registration and login feature.
 *
 * @author Praise Nemberi ST10542243
 */
public class QuickChat {

    private static final String RECIPIENT_OK = "Cell phone number successfully captured.";
    private static final String LENGTH_OK = "Message ready to send.";

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Login login = new Login();

            System.out.print("Enter your first name: ");
            String firstName = input.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = input.nextLine();

            System.out.print("Enter a username: ");
            String username = input.nextLine();
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
            }

            System.out.print("Enter a password: ");
            String password = input.nextLine();
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
            }

            System.out.print("Enter your cell number: ");
            String cellNumber = input.nextLine();
            if (login.checkCellPhoneNumber(cellNumber)) {
                System.out.println("Cell phone number successfully added.");
            }

            System.out.println();
            System.out.println(login.registerUser(firstName, lastName, username, password, cellNumber));
            System.out.println();
            System.out.print("Login - username: ");
            String loginUsername = input.nextLine();

            System.out.print("Login - password: ");
            String loginPassword = input.nextLine();

            boolean loginSuccess = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(loginSuccess));

            if (!loginSuccess) {
                return;
            }

            System.out.println();
            System.out.println("Welcome to QuickChat.");

            int numMessages = readNumber(input,
                    "How many messages do you want to enter? ", 1);
            boolean messagesEntered = false;

            boolean running = true;
            while (running) {
                System.out.println();
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                int choice = readNumber(input, "Choose an option: ", 1);

                switch (choice) {
                    case 1:
                        if (messagesEntered) {
                            System.out.println("You have already entered all " + numMessages + " messages.");
                        } else {
                            enterMessages(input, numMessages);
                            messagesEntered = true;
                        }
                        break;
                    case 2:
                        System.out.println("Coming Soon.");
                        break;
                    case 3:
                        running = false;
                        System.out.println("Goodbye.");
                        break;
                    default:
                        System.out.println("Invalid option; please choose 1, 2 or 3.");
                }
            }
        }
    }

    /**
     * Lets the user enter the number of messages they chose at the start, using
     * a for loop. The loop counter is used as the message number.
     *
     * @param input the Scanner used to read the keyboard
     * @param numMessages how many messages the user wants to enter
     */
    private static void enterMessages(Scanner input, int numMessages) {
        for (int i = 0; i < numMessages; i++) {
            System.out.println();
            System.out.println("Message " + (i + 1) + " of " + numMessages);

            Message message = readValidMessage(input, i);
            System.out.println(message.displayMessageID());
            System.out.println("Message Hash: " + message.createMessageHash());
        }
    }

    /**
     * Asks for a recipient and a message until both are valid.
     *
     * @param input the Scanner used to read the keyboard
     * @param messageNumber the number of this message, taken from the loop
     * @return a message with a valid recipient and length
     */
    private static Message readValidMessage(Scanner input, int messageNumber) {
        Message message;
        boolean valid;

        do {
            System.out.print("Enter the recipient's cell number: ");
            String recipient = input.nextLine().trim();
            System.out.print("Enter your message: ");
            String text = input.nextLine();

            message = new Message(Message.generateMessageID(), messageNumber,
                    recipient, text);
            String recipientResult = message.checkRecipientCell();
            String lengthResult = message.checkMessageLength();
            System.out.println(recipientResult);
            System.out.println(lengthResult);

            valid = recipientResult.equals(RECIPIENT_OK)
                    && lengthResult.equals(LENGTH_OK);
        } while (!valid);

        return message;
    }

    /**
     * Asks the user for a whole number and keeps asking until a valid number of
     * at least the minimum is entered.
     *
     * @param input the Scanner used to read the keyboard
     * @param prompt the question shown to the user
     * @param minimum the smallest number that is accepted
     * @return the number entered by the user
     */
    private static int readNumber(Scanner input, String prompt, int minimum) {
        int number = 0;
        boolean validNumber = false;

        while (!validNumber) {
            System.out.print(prompt);
            try {
                number = Integer.parseInt(input.nextLine().trim());
                if (number >= minimum) {
                    validNumber = true;
                } else {
                    System.out.println("Please enter a number of " + minimum + " or more.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
        return number;
    }
}
