/* Name: Carter Gerlach
Course: CNT 4714 – Spring 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday February 1, 2026
*/

import java.io.*;
import java.util.*;

public class ManageInventory {

    private List<InventoryItem> inventory = new ArrayList<>();
    private ShoppingCart cart = new ShoppingCart();

    public ManageInventory(String filename) throws IOException {
        loadInventory(filename);
    }

    private void loadInventory(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] properties = line.split(",");

            String id = properties[0].trim();
            String description = properties[1].trim().replace("\"", "");
            boolean inStock = Boolean.parseBoolean(properties[2].trim());
            int quantity = Integer.parseInt(properties[3].trim());
            double price = Double.parseDouble(properties[4].trim());

            inventory.add(new InventoryItem(id, description, inStock, quantity, price));
        }
        reader.close();
    }

    public InventoryItem getItem(String id) {
        for (InventoryItem item : inventory) {
            if (item.getID().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public ShoppingCart getCart() {
        return cart;
    }
}
