/* Name: Carter Gerlach
Course: CNT 4714 – Spring 2026
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday February 1, 2026
*/

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class NileDotComGUI extends JFrame {

	// Dimensions of the GUI
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    // Button and labels on the GUI
    private JLabel controlLabel, shoppingCartLabel, subtotalLabel, idLabel, quantityLabel, detailsLabel;
    private JButton blankButton, searchButton, addToCartButton,
            deleteButton, checkOutButton, emptyCartButton, exitButton;
    
    // Cart Fields
    private JTextField cartField1, cartField2, cartField3, cartField4, cartField5;
    
    // Input fields
    private JTextField searchIDField;
    private JTextField quantityField;
    
    // Output Fields (item details and current subtotal)
    private JTextField detailsField;
    private JTextField currentSubtotalField;

    private ManageInventory inventory;
    private InventoryItem currentItem;
    private ShoppingCart cart;

    // Current sub total and number of items the user has
    private double runningSubtotal = 0.0;
    static int itemCounter = 0;

    public NileDotComGUI() {
    	// Title of the GUI
        setTitle("Nile.com - Carter's Version");
        setSize(WIDTH, HEIGHT);

        // Load the inventory file
        try {
            inventory = new ManageInventory("inventory.csv");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading inventory file.",
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
        
        // Create a new shopping cart
        cart = new ShoppingCart();

        // Setup the intial GUI, including buttons, labels, and text fields
        blankButton = new JButton(" ");
        controlLabel = new JLabel(" User Controls ", SwingConstants.RIGHT);
        
        // Label for shopping cart
        shoppingCartLabel = new JLabel("Shopping Cart is Currently Empty!!");
        
        // Search button 
        searchButton = new JButton("Search for Item #" + (itemCounter + 1));
        searchButton.addActionListener(new SearchButtonHandler());
        
        // Add to cart button 
        addToCartButton = new JButton("Add Item #" + (itemCounter + 1) + " To Cart");
        addToCartButton.addActionListener(new AddToCartButtonHandler());

        // Delete item button
        deleteButton = new JButton("Delete Last Item Added to the Cart");
        deleteButton.addActionListener(new DeleteButtonHandler());

        // Checkout button 
        checkOutButton = new JButton("Check Out");
        checkOutButton.addActionListener(new CheckOutButtonHandler());
        
        // Empty the cart/start over button
        emptyCartButton = new JButton("Empty Cart- Begin a New Order");
        emptyCartButton.addActionListener(new EmptyCartButtonHandler());

        // Exit Button
        exitButton = new JButton("Exit - Close the App");
        exitButton.addActionListener(new ExitButtonHandler());
        
        blankButton.setBackground(Color.DARK_GRAY);
        blankButton.setVisible(false);

        // Cart Fields, slots for each item
        cartField1 = new JTextField();
        cartField2 = new JTextField();
        cartField3 = new JTextField();
        cartField4 = new JTextField();
        cartField5 = new JTextField();

        // User cannot edit cart contents 
        cartField1.setEditable(false);
        cartField2.setEditable(false);
        cartField3.setEditable(false);
        cartField4.setEditable(false);
        cartField5.setEditable(false);

        // Input text fields 
        searchIDField = new JTextField();
        quantityField = new JTextField();

        // out fields, item details and current sub total 
        detailsField = new JTextField();
        currentSubtotalField = new JTextField();
        
        // user cannot edit the item details and subtotal
        detailsField.setEditable(false);
        currentSubtotalField.setEditable(false);
        
        // Disable the add to cart, delete, and checkout buttons initially until conditions are met
        addToCartButton.setEnabled(false);
        deleteButton.setEnabled(false);
        checkOutButton.setEnabled(false);
        
        // Set up GUI layout
        Container pane = getContentPane();

        GridLayout grid5by2 = new GridLayout(5, 2, 8, 4);
        GridLayout grid7by2 = new GridLayout(7, 2, 8, 4);

        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        northPanel.setLayout(grid5by2);
        centerPanel.setLayout(grid7by2);
        southPanel.setLayout(grid5by2);

        pane.add(northPanel, BorderLayout.NORTH);
        pane.add(centerPanel, BorderLayout.CENTER);
        pane.add(southPanel, BorderLayout.SOUTH);

        // GUI's background colors
        pane.setBackground(Color.DARK_GRAY);
        northPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.setBackground(Color.DARK_GRAY);
        southPanel.setBackground(Color.LIGHT_GRAY);
        
        // Position Cart Label
        centerPanel.add(shoppingCartLabel);
        shoppingCartLabel.setHorizontalAlignment(JLabel.CENTER);
        shoppingCartLabel.setForeground(Color.RED);
        shoppingCartLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        // Position cart fields
        centerPanel.add(cartField1);
        centerPanel.add(cartField2);
        centerPanel.add(cartField3);
        centerPanel.add(cartField4);
        centerPanel.add(cartField5);

        southPanel.add(controlLabel);
        controlLabel.setHorizontalAlignment(JLabel.CENTER);
        controlLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        southPanel.add(blankButton);

        // Position the buttons 
        southPanel.add(searchButton);
        southPanel.add(addToCartButton);
        southPanel.add(deleteButton);
        southPanel.add(checkOutButton);
        southPanel.add(emptyCartButton);
        southPanel.add(exitButton);

        // Position the labels and fields for user input 
        idLabel = new JLabel("Enter Item ID for Item #1: ");
        northPanel.add(idLabel);
        northPanel.add(searchIDField);

        quantityLabel = new JLabel("Enter Quantity for Item #1: ");
        northPanel.add(quantityLabel);
        northPanel.add(quantityField);

        // Position the labels and fields for output 
        detailsLabel = new JLabel("Details for Item #1: ");
        northPanel.add(detailsLabel);
        northPanel.add(detailsField);

        subtotalLabel = new JLabel("Current Subtotal for 0 item(s):");
        northPanel.add(subtotalLabel);
        northPanel.add(currentSubtotalField);
    }

    // Search button handler 
    private class SearchButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        	// Read the Item id entered by the user and remove leading/trailing spaces
            String itemId = searchIDField.getText().trim();
            // If the item id is empty (nothing entered) and user tries to search, display error message
            if (itemId.isEmpty()) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Please enter an Item ID.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int quantity;
            try {
            	// Read the quantity the user entered
                quantity = Integer.parseInt(quantityField.getText().trim());
                // If the quantity is 0, less than 0, or not a number, display an error
                if (quantity <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Please enter a valid quantity greater than 0.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                // clear quantity field
                quantityField.setText("");
                return;
            }
            
            // If the value entered by user is valid, search inventory for item
            InventoryItem item = inventory.getItem(itemId);

            // If item is not found (null) in the inventory, display error message
            if (item == null) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Item not found. Item ID " + itemId + " Not in the file.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                // clear the input fields if item is not in inventory
                searchIDField.setText("");
                quantityField.setText("");
                return;
            }

            // Check to see if item is in stock, if not, display error message
            if (!item.isInStock()) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Item is currently out of stock. Please try a different item.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                // clear the input fields if item is out of stock
                searchIDField.setText("");
                quantityField.setText("");
                return;
            }

            // Check to see if the requested quantity exceeds the amount in stock, if so display error
            if (quantity > item.getQuantity()) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Insufficient Stock. Only " + item.getQuantity() + " units available. Please reduce quantity.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                // clear the quantity field
                quantityField.setText("");
                return;
            }

            // If search was successful, store item so we can add it to cart later 
            currentItem = item;

            // Create a temporary ShoppingCartItem object to calculate the discount and sub total
            ShoppingCartItem temp = new ShoppingCartItem(item, quantity);
            double discount = temp.getDiscountRate();
            double subtotal = temp.getFinalSubtotal();

            // Build a string to display item's description in the GUI
            String details =
                    item.getID() + " " + "\"" + 
                            item.getDescription() + "\"" + " " +
                            "$" + String.format("%.2f", item.getPrice()) + " " +
                            quantity + " " +
                            (int) (discount * 100) + "% " +
                            "$" + String.format("%.2f", subtotal);

            // display details of the item
            detailsField.setText(details);
            detailsLabel.setText("Details for Item #" + (itemCounter + 1) + ":");
            
            // display search button since this search is complete
            searchButton.setEnabled(false);
            // enable add to cart button since we found the item we are looking for 
            addToCartButton.setEnabled(true);
        }
    }

    // Add to cart button handler
    private class AddToCartButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        	// If the item from search is blank, display error
            if (currentItem == null) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Please search for an item before adding it to the cart.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int quantity;
            try {
            	// do quantity check again, display error if it fails
                quantity = Integer.parseInt(quantityField.getText().trim());
                if (quantity <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Please enter a valid quantity greater than 0.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Quantity check again 
            if (quantity > currentItem.getQuantity()) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Insufficient Stock. Only " + currentItem.getQuantity() + " units available. Please reduce quantity.",
                        "Nile.com Error",
                        JOptionPane.ERROR_MESSAGE
                );
                quantityField.setText("");
                return;
            }
            
            // If we have more than 5 items, cart is full. No more items allowed. Display error.
            if (itemCounter >= 5) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Shopping Cart is full. Maximum 5 items allowed.",
                        "Nile.com Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            // Create ShoppingCartItem object so we can add items to the cart
            ShoppingCartItem cartItem = new ShoppingCartItem(currentItem, quantity);
           
            // Add item to the cart
            cart.addItem(currentItem, quantity);

            // Increment item counter 
            itemCounter++;
            
            if (itemCounter == 5) { 
            	
            	// Cart is full, disable adding/searching 
            	searchIDField.setEnabled(false);
            	searchIDField.setVisible(false);
            	quantityField.setEnabled(false); 
            	quantityField.setVisible(false);
            	searchButton.setEnabled(false); 
            	addToCartButton.setEnabled(false); 
            	
            	// Indicate that cart is full
            	shoppingCartLabel.setText("Your Shopping Cart Currently Contains " + itemCounter + " Item(s), Cart is Full!!"); 
            }

            // Create string to display item details in the cart 
            String cartText =
                    "Item " + itemCounter + " - SKU: " +
                            currentItem.getID() + ", Desc: " + "\"" +
                            currentItem.getDescription() + "\"" + ", Price Ea. $" +
                            String.format("%.2f", currentItem.getPrice()) +
                            ", Qty: " + quantity +
                            ", Total: $" + String.format("%.2f", cartItem.getFinalSubtotal());

            // Determine which slot to put the item in based on how full it is
            switch (itemCounter) {
                case 1: cartField1.setText(cartText); break;
                case 2: cartField2.setText(cartText); break;
                case 3: cartField3.setText(cartText); break;
                case 4: cartField4.setText(cartText); break;
                case 5: cartField5.setText(cartText); break;
            }
            
            // Re-calculate sub total and then display it
            runningSubtotal += cartItem.getFinalSubtotal();
            currentSubtotalField.setText("$" + String.format("%.2f", runningSubtotal));

            // Update labels after item count increases
            shoppingCartLabel.setText("Your Shopping Cart Currently Contains " + itemCounter + " Item(s): ");
            searchButton.setText("Search for Item #" + (itemCounter + 1));
            addToCartButton.setText("Add Item #" + (itemCounter + 1) + " To Cart");
            idLabel.setText("Enter Item ID for Item #" + (itemCounter + 1) + ":");
            quantityLabel.setText("Enter Quantity for Item #" + (itemCounter + 1) + ":");
            subtotalLabel.setText("Current Subtotal for " + itemCounter + " item(s): ");

            // clear input fields 
            searchIDField.setText("");
            quantityField.setText("");
            
            // disable search and add if the cart is full
            if (itemCounter < 5) {
                searchButton.setEnabled(true);
                addToCartButton.setEnabled(false);
            }
            // allow delete and checkout to be enabled since at least 1 item is in cart now 
            deleteButton.setEnabled(true);
            checkOutButton.setEnabled(true);


            currentItem = null;
        }
    }

    // Delete button handler 
    private class DeleteButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        	// If the item counter is at 0, the cart is empty, so we cannot delete anything 
            if (itemCounter == 0) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "There are no items to delete.",
                        "Delete Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Remove the last item added from the cart
            ShoppingCartItem removed = cart.removeTheLastItem();

            // Re-calculate sub total after item is removed
            if (removed != null) {
                runningSubtotal -= removed.getFinalSubtotal();
                runningSubtotal = Math.max(0, runningSubtotal);
            }

            // Clear the last field based on the amount of items in cart
            switch (itemCounter) {
                case 1: cartField1.setText(""); break;
                case 2: cartField2.setText(""); break;
                case 3: cartField3.setText(""); break;
                case 4: cartField4.setText(""); break;
                case 5: cartField5.setText(""); break;
            }

            // Decrement item counter
            itemCounter--;

            // Display new sub total
            currentSubtotalField.setText("$" + String.format("%.2f", runningSubtotal));

            // If there is no items left, indicate that cart is empty. Otherwise, display number of items in cart
            if (itemCounter == 0) {
                shoppingCartLabel.setText("Shopping Cart is Currently Empty!!");
            } else {
                shoppingCartLabel.setText("Your Shopping Cart Currently Contains " + itemCounter + " item(s): ");
            }

            // Update labels to reflect one less item 
            subtotalLabel.setText("Current Subtotal for " + itemCounter + " item(s):");
            idLabel.setText("Enter Item ID for Item #" + (itemCounter + 1) + ":");
            quantityLabel.setText("Enter Quantity for Item #" + (itemCounter + 1) + ":");

            searchButton.setText("Search for Item #" + (itemCounter + 1));
            addToCartButton.setText("Add Item #" + (itemCounter + 1) + " To Cart");

            // Clear the details field
            detailsField.setText("");
        }
    }

    // checkout button handler
    private class CheckOutButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        	// If the cart is empty, we cannot check out, display error message
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Your cart is empty!",
                        "Checkout Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Build invoice 
            StringBuilder invoice = new StringBuilder();
            invoice.append("Nile Dot Com - FINAL INVOICE\n\n");
            invoice.append("Date: ").append(TransactionLogger.getFormattedDateTime()).append("\n");
            invoice.append("Number of line items: ").append(cart.getItems().size()).append("\n\n");
            invoice.append("Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n");

            double orderSubtotal = 0.0;
            int itemNumber = 1;

            for (ShoppingCartItem sci : cart.getItems()) {
                double subtotal = sci.getFinalSubtotal();
                orderSubtotal += subtotal;

                invoice.append(String.format(
                        "%d. %s \"%s\" $%.2f %d %.0f%% $%.2f\n",
                        itemNumber++,
                        sci.getItem().getID(),
                        sci.getItem().getDescription(),
                        sci.getItem().getPrice(),
                        sci.getQuantity(),
                        sci.getDiscountRate() * 100,
                        subtotal
                ));
            }

            // Calculate order total with tax 
            double taxRate = 0.06;
            double taxAmount = orderSubtotal * taxRate;
            double orderTotal = orderSubtotal + taxAmount;

            // Display tax rate and final total 
            invoice.append(String.format("\nOrder subtotal: $%.2f\n", orderSubtotal));
            invoice.append(String.format("Tax rate: %.0f%%\n", taxRate * 100));
            invoice.append(String.format("Tax amount: $%.2f\n", taxAmount));
            invoice.append(String.format("\nORDER TOTAL: $%.2f\n\n", orderTotal));
            invoice.append("Thanks for shopping at Nile Dot Com!");

            JTextArea textArea = new JTextArea(invoice.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));

            JOptionPane.showMessageDialog(
                    NileDotComGUI.this,
                    scrollPane,
                    "Final Invoice",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Log transaction to csv file. Display message if there was an issue
            String transactionId = TransactionLogger.generateTransactionId();
            try {
                TransactionLogger.logTransaction(transactionId, cart.getItems());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "Error writing to transactions.csv",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE
                );
                
            }
            
            // Disable input fields and make them disappear
            searchIDField.setEnabled(false);
            searchIDField.setVisible(false);
            quantityField.setEnabled(false);
            quantityField.setVisible(false);
            
            // Disable buttons except for exit and start over
            searchButton.setEnabled(false);
            deleteButton.setEnabled(false);
            checkOutButton.setEnabled(false);
            

        }
    }

    // Empty cart button handler 
    private class EmptyCartButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        	// If not items in cart, cart is empty already. 
            if (itemCounter == 0) {
                JOptionPane.showMessageDialog(
                        NileDotComGUI.this,
                        "The cart is already empty.",
                        "Empty Cart",
                        JOptionPane.INFORMATION_MESSAGE
                );
                // Fix for bug where if user checks out, then deletes last item, leaving cart empty, 
                // but then cannot start new order
                searchIDField.setEnabled(true);
                searchIDField.setVisible(true);
                searchIDField.setText("");
                quantityField.setEnabled(true);
                quantityField.setVisible(true);
                quantityField.setText("");
                detailsField.setText("");
                deleteButton.setEnabled(false);
                checkOutButton.setEnabled(false);
                searchButton.setEnabled(true);
                addToCartButton.setEnabled(false);
                currentSubtotalField.setText("");
                runningSubtotal = 0.0;
                return;
            }

            // Clear out the cart
            cart.clear();

            // Clear out cart fields 
            cartField1.setText("");
            cartField2.setText("");
            cartField3.setText("");
            cartField4.setText("");
            cartField5.setText("");

            // set item counter and sub total to 0
            itemCounter = 0;
            runningSubtotal = 0.0;
            
            // Reset labels and fields 
            shoppingCartLabel.setText("Shopping Cart is Currently Empty!!");
            subtotalLabel.setText("Current Subtotal for 0 item(s):");
            idLabel.setText("Enter Item ID for Item #1:");
            quantityLabel.setText("Enter Quantity for Item #1:");
            detailsLabel.setText("Details for Item #1: ");
            searchButton.setText("Search for Item #1");
            addToCartButton.setText("Add Item #1 To Cart");

            detailsField.setText("");
            currentSubtotalField.setText("");
            
            searchIDField.setEnabled(true);
            searchIDField.setVisible(true);
            quantityField.setEnabled(true);
            quantityField.setVisible(true);
            
            // Disable delete and checkout since cart will be empty. Enable searching again..
            deleteButton.setEnabled(false);
            checkOutButton.setEnabled(false);
            searchButton.setEnabled(true);
            
        }
    }

    // Exit button handler
    private class ExitButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        JFrame store = new NileDotComGUI();
        store.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        store.setVisible(true);
    }
}
