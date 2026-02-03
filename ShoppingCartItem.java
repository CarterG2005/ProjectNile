/* Name: Carter Gerlach
Course: CNT 4714 – Spring 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday February 1, 2026
*/

public class ShoppingCartItem {

    private InventoryItem item;
    private int quantity;
    private double discountRate;

    public ShoppingCartItem(InventoryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.discountRate = calculateDiscount(quantity);
    }

    private double calculateDiscount(int quantity) {
        if (quantity >= 15) return 0.20;
        if (quantity >= 10) return 0.15;
        if (quantity >= 5)  return 0.10;
        return 0.0;
    }

    public InventoryItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public double getFinalSubtotal() {
        double base = item.getPrice() * quantity;
        return base * (1 - discountRate);
    }
}
