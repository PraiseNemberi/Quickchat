package za.co.quickchat.quickchat;

import java.util.Scanner;

/**
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

            System.out.print("Enter a password: ");
            String password = input.nextLine();

            System.out.print("Enter your cell number: ");
            String cellNumber = input.nextLine();

            System.out.println();
            System.out.println(login.registerUser(firstName, lastName, username, password, cellNumber));

        }
    }
}
