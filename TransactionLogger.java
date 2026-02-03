/* Name: Carter Gerlach
Course: CNT 4714 – Spring 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday February 1, 2026
*/

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionLogger {

    public static String generateTransactionId() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        return ZonedDateTime.now().format(formatter);
    }

    public static String getFormattedDateTime() {
        ZonedDateTime now =
                ZonedDateTime.now(java.time.ZoneId.of("America/New_York"));
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMMM d, yyyy, h:mm:ss a z");
        return now.format(formatter);
    }

    public static void logTransaction(
            String transactionId,
            List<ShoppingCartItem> items) throws IOException {

        FileWriter writer = new FileWriter("transactions.csv", true);

        for (ShoppingCartItem item : items) {
            writer.write(
                transactionId + ", " +
                item.getItem().getID() + ", " +
                "\"" + item.getItem().getDescription() + "\"" + ", " +
                String.format("%.2f", item.getItem().getPrice()) + ", " +
                item.getQuantity() + ", " +
                String.format("%.2f", item.getDiscountRate()) + ", " +
                "$" + String.format("%.2f", item.getFinalSubtotal()) + ", " +
                getFormattedDateTime() + "\n"
            );
        }
        // Add a blank line to separate this transaction from the next 
        writer.write("\n");
        
        writer.close();
    }
}
