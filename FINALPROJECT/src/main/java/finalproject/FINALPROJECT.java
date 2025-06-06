/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package finalproject;

/**
 *
 * @author Madeline
 */
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FINALPROJECT {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> users = new ArrayList<>();
    static ArrayList<String> passwords = new ArrayList<>();

    static ArrayList<String> productNames = new ArrayList<>(List.of(
            "Petitllao (Small Yogurt)",
            "Tub (Medium Yogurt)",
            "Sanum (Yogurt + Fruits + Crunch)",
            "Topping: Fresh Strawberries",
            "Topping: Chocolate Sauce",
            "Topping: Crunchy Muesli"
    ));

    static ArrayList<Double> productPrices = new ArrayList<>(List.of(99.00, 199.00, 249.00, 29.00, 29.00, 29.00));
    static ArrayList<Integer> productStocks = new ArrayList<>(List.of(50, 50, 50, 50, 50, 50));

    public static void main(String[] args) {
        String currentUser = "";

        while (true) {
            System.out.println("==============================");
            System.out.println("        LLAOLLAO STORE         ");
            System.out.println("==============================");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    signUp();
                    break;
                case "2":
                    currentUser = login();
                    if (!currentUser.isEmpty()) {
                        shoppingMenu(currentUser);
                    }
                    break;
                case "3":
                    System.out.println("Thank you for visiting Llaollao Store!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void signUp() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        users.add(username);
        passwords.add(password);
        System.out.println("Sign up successful!\n");
    }

    public static String login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(username) && passwords.get(i).equals(password)) {
                System.out.println("Login successful!\n");
                return username;
            }
        }
        System.out.println("Invalid credentials.\n");
        return "";
    }

    public static void shoppingMenu(String user) {
        ArrayList<String> cartItems = new ArrayList<>();
        ArrayList<Integer> cartQty = new ArrayList<>();
        boolean shopping = true;

        while (shopping) {
            displayProducts();
            System.out.println("Options: 1) Add 2) Update 3) Remove 4) View Cart 5) Checkout 6) Exit");
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    try {
                        System.out.print("Enter product number to add: ");
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        if (index >= 0 && index < productNames.size()) {
                            System.out.print("Enter quantity: ");
                            int qty = Integer.parseInt(scanner.nextLine());
                            if (qty <= productStocks.get(index)) {
                                cartItems.add(productNames.get(index));
                                cartQty.add(qty);
                                productStocks.set(index, productStocks.get(index) - qty);
                                System.out.println("Added to cart.\n");
                            } else {
                                System.out.println("Insufficient stock.\n");
                            }
                        } else {
                            System.out.println("Invalid product number.\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input.\n");
                    }
                    break;
                case "2":
                    try {
                        System.out.print("Enter cart item number to update: ");
                        int updateIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if (updateIndex >= 0 && updateIndex < cartItems.size()) {
                            System.out.print("Enter new quantity: ");
                            int newQty = Integer.parseInt(scanner.nextLine());
                            cartQty.set(updateIndex, newQty);
                            System.out.println("Cart updated.\n");
                        } else {
                            System.out.println("Invalid cart item number.\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input.\n");
                    }
                    break;
                case "3":
                    try {
                        System.out.print("Enter cart item number to remove: ");
                        int removeIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if (removeIndex >= 0 && removeIndex < cartItems.size()) {
                            cartItems.remove(removeIndex);
                            cartQty.remove(removeIndex);
                            System.out.println("Item removed from cart.\n");
                        } else {
                            System.out.println("Invalid cart item number.\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input.\n");
                    }
                    break;
                case "4":
                    displayCart(cartItems, cartQty);
                    break;
                case "5":
                    checkout(user, cartItems, cartQty);
                    cartItems.clear();
                    cartQty.clear();
                    break;
                case "6":
                    shopping = false;
                    break;
                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }

    public static void displayProducts() {
        System.out.println("========================================");
        System.out.println("       WELCOME TO LLAOLLAO STORE        ");
        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("                  MENU                  ");
        System.out.println("========================================");

        for (int i = 0; i < productNames.size(); i++) {
            System.out.printf(" %d. %-35s ₱%.2f | Stock: %3d\n",
                    i + 1, productNames.get(i), productPrices.get(i), productStocks.get(i));
        }
    }

    public static void displayCart(ArrayList<String> cartItems, ArrayList<Integer> cartQty) {
        System.out.println("\nYour Cart:");
        for (int i = 0; i < cartItems.size(); i++) {
            System.out.printf(" %d. %s x%d\n", i + 1, cartItems.get(i), cartQty.get(i));
        }
    }

    public static void checkout(String user, ArrayList<String> cartItems, ArrayList<Integer> cartQty) {
        double total = 0;
        StringBuilder receipt = new StringBuilder();

        System.out.println("\nRECEIPT:");
        for (int i = 0; i < cartItems.size(); i++) {
            int index = productNames.indexOf(cartItems.get(i));
            double subtotal = productPrices.get(index) * cartQty.get(i);
            total += subtotal;
            String item = cartItems.get(i) + " x" + cartQty.get(i) + " @ ₱" + productPrices.get(index);
            System.out.println(item + " - ₱" + String.format("%.2f", subtotal));
            receipt.append(item).append(" - ₱").append(String.format("%.2f", subtotal)).append("\n");
        }

        System.out.printf("Total: ₱%.2f\n", total);
        System.out.print("Enter payment amount: ");
        double payment = Double.parseDouble(scanner.nextLine());
        if (payment >= total) {
            double change = payment - total;
            System.out.printf("Change: ₱%.2f\n", change);
            logTransaction(user, receipt.toString(), total);
        } else {
            System.out.println("Insufficient payment. Transaction cancelled.\n");
        }
    }

    public static void logTransaction(String user, String items, double total) {
        try {
            FileWriter writer = new FileWriter("transactions.txt", true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            writer.write("Date/Time: " + date + "\n");
            writer.write("User: " + user + "\n");
            writer.write("Items:\n" + items);
            writer.write("Total: ₱" + String.format("%.2f", total) + "\n");
            writer.write("--------------------------\n");
            writer.close();
            System.out.println("Transaction saved successfully!\n");
        } catch (IOException e) {
            System.out.println("Error saving transaction.\n");
        }
    }
} 
