/* Name: Carter Gerlach
Course: CNT 4714 – Spring 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday February 1, 2026
*/

public class InventoryItem {

    private String id;
    private String description;
    private boolean inStock;
    private int quantity;
    private double price;

    public InventoryItem(String id, String description, boolean inStock,
                         int quantity, double price) {
        this.id = id;
        this.description = description;
        this.inStock = inStock;
        this.quantity = quantity;
        this.price = price;
    }

    public String getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isInStock() {
        return inStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

}
