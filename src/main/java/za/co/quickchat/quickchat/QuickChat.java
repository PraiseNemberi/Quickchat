

package za.co.quickchat.quickchat;

import java.util.Scanner;
/**
 *
 * @author Praise Nemberi ST10542243
 */
public class QuickChat {

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Enter a username: ");
            String username = input.nextLine();
            
            System.out.print("Enter a Password: ");
            String password = input.nextLine();
            
            System.out.print("Enter your cell number: ");
            String cellNumber = input.nextLine();
            
            
            
            System.out.println("Username: " + username);
            System.out.println("Password: "  + password);
            System.out.println("Cell number: " + cellNumber);
        }
    }
}
