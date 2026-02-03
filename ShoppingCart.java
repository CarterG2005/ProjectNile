/* Name: Carter Gerlach
Course: CNT 4714 – Spring 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday February 1, 2026
*/

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<ShoppingCartItem> items = new ArrayList<>();

    public void addItem(InventoryItem item, int quantity) {
        items.add(new ShoppingCartItem(item, quantity));
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        double total = 0;
        for (ShoppingCartItem item : items) {
            total += item.getFinalSubtotal();
        }
        return total;
    }
    
    public ShoppingCartItem removeTheLastItem() {
        if (items.isEmpty()) {
            return null;
        }
        return items.remove(items.size() - 1);
    }

    public void clear() {
        items.clear();
    }
}
