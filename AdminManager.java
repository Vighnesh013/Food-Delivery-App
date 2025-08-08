package com.aurionpro.foodOrderingApp.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AdminManager {
    private final String ADMIN_FILE = "Admins.txt";
    private MenuManager menuManager;
    private CustomerDetailsManager customerManager;

    public AdminManager(MenuManager menuManager, CustomerDetailsManager customerManager) {
        this.menuManager = menuManager;
        this.customerManager = customerManager;
        createAdminFileIfNotExists();
    }

    private void createAdminFileIfNotExists() {
        File file = new File(ADMIN_FILE);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("admin,1234" + System.lineSeparator());
            } catch (IOException e) {
                System.out.println("Could not create admin file: " + e.getMessage());
            }
        }
    }

    public boolean authenticateAdmin(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading admin file: " + e.getMessage());
        }
        return false;
    }

    public void addAdmin(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    System.out.println("Username already exists. Please choose a different username.");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading admin file: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(ADMIN_FILE, true)) {
            writer.write(username + "," + password + System.lineSeparator());
            System.out.println("New admin added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to admin file: " + e.getMessage());
        }
    }

    public void showAdminMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\n--- Admin Dashboard ---");
                System.out.println("1. View Menu");
                System.out.println("2. Add Menu Item");
                System.out.println("3. Remove Menu Item");
                System.out.println("4. View Registered Customers");
                System.out.println("5. View Delivery Partners");
                System.out.println("6. Add New Admin");
                System.out.println("7. Logout");

                int choice = -1;
                while (true) {
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                        if (choice < 1 || choice > 7) {
                            System.out.println("Invalid choice. Please select a valid option (1-7).");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid number for the option!");
                    }
                }

                switch (choice) {
                    case 1:
                        menuManager.displayMenu();
                        break;
                    case 2:
                        System.out.print("Enter new item name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double price = 0;
                        while (true) {
                            try {
                                price = Double.parseDouble(scanner.nextLine());
                                if (price <= 0) {
                                    System.out.println("Price must be greater than zero. Try again.");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Enter a valid price.");
                            }
                        }
                        menuManager.addItem(new MenuItem(name, price));
                        System.out.println("Item added successfully.");
                        break;
                    case 3:
                        menuManager.displayMenu();
                        System.out.print("Enter item number to remove: ");
                        int index = -1;
                        while (true) {
                            try {
                                index = Integer.parseInt(scanner.nextLine()) - 1;
                                if (index < 0 || index >= menuManager.getMenuSize()) {
                                    System.out.println("Invalid item number. Try again.");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Enter a valid number.");
                            }
                        }
                        if (menuManager.removeItem(index)) {
                            System.out.println("Item removed.");
                        } else {
                            System.out.println("Invalid item number.");
                        }
                        break;
                    case 4:
                        try {
                            List<String> customers = customerManager.getAllCustomerNames();
                            System.out.println("Registered Customers:");
                            for (String customer : customers) {
                                System.out.println("- " + customer);
                            }
                        } catch (Exception e) {
                            System.out.println("Error reading customers: " + e.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            System.out.println("Delivery Partners:");
                            for (String partner : DeliveryPartnerManager.getAllPartners()) {
                                System.out.println("- " + partner);
                            }
                        } catch (Exception e) {
                            System.out.println("Error reading delivery partners: " + e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.print("Enter new admin username: ");
                        String adminUser = scanner.nextLine();
                        System.out.print("Enter new admin password: ");
                        String adminPass = scanner.nextLine();
                        addAdmin(adminUser, adminPass);
                        break;
                    case 7:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
