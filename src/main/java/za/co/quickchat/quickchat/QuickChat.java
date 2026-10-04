package za.co.quickchat.quickchat;

import java.util.Scanner;

/**
 * Console application for the QuickChat registration and login feature.
 *
 * @author Praise Nemberi ST10542243
 */
public class QuickChat {

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
        }

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
