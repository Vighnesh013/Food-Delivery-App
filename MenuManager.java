package com.aurionpro.foodOrderingApp.model;

import java.io.*;
import java.util.*;

public class MenuManager {
    private final String MENU_FILE = "MenuItems.txt";

    public void displayMenu() {
        List<MenuItem> menuItems = loadMenu();
        System.out.println("\n------ MENU ------");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - ₹" + item.getPrice());
        }
    }

    public List<MenuItem> loadMenu() {
        List<MenuItem> menu = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    menu.add(new MenuItem(parts[0], Double.parseDouble(parts[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading menu: " + e.getMessage());
        }
        return menu;
    }

    public void addItem(MenuItem item) {
        try (FileWriter writer = new FileWriter(MENU_FILE, true)) {
            writer.write(item.getName() + "," + item.getPrice() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error writing to menu: " + e.getMessage());
        }
    }

    public boolean removeItem(int index) {
        List<MenuItem> menu = loadMenu();
        if (index < 0 || index >= menu.size()) {
            return false;
        }

        menu.remove(index);

        try (FileWriter writer = new FileWriter(MENU_FILE, false)) {
            for (MenuItem item : menu) {
                writer.write(item.getName() + "," + item.getPrice() + System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error updating menu file: " + e.getMessage());
            return false;
        }
    }

    public MenuItem getMenuItem(int index) {
        List<MenuItem> menu = loadMenu();
        if (index >= 0 && index < menu.size()) {
            return menu.get(index);
        }
        return null;
    }

    public List<MenuItem> getMenuItems() {
        return loadMenu();
    }
}
