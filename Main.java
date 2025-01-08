import java.io.*;
import java.util.*;

class Product implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Quantity: " + quantity + ", Price: $" + price;
    }
}

public class InventoryManagement {
    private static final String FILE_NAME = "InventoryData.txt";
    private static Map<Integer, Product> inventory = new HashMap<>();

    public static void main(String[] args) {
        loadInventory();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Inventory Management System ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Remove Product");
            System.out.println("4. View Inventory");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addProduct(scanner);
                case 2 -> updateProduct(scanner);
                case 3 -> removeProduct(scanner);
                case 4 -> viewInventory();
                case 5 -> generateReport();
                case 6 -> System.out.println("Exiting the system...");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);

        saveInventory();
        scanner.close();
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        Product product = new Product(id, name, quantity, price);
        inventory.put(id, product);
        System.out.println("Product added successfully!");
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();

        if (inventory.containsKey(id)) {
            System.out.print("Enter new quantity: ");
            int quantity = scanner.nextInt();
            inventory.get(id).setQuantity(quantity);
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void removeProduct(Scanner scanner) {
        System.out.print("Enter Product ID to remove: ");
        int id = scanner.nextInt();

        if (inventory.remove(id) != null) {
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void viewInventory() {
        System.out.println("\n=== Current Inventory ===");
        for (Product product : inventory.values()) {
            System.out.println(product);
        }
    }

    private static void generateReport() {
        System.out.println("\n=== Inventory Report ===");
        double totalValue = 0;
        for (Product product : inventory.values()) {
            double productValue = product.getQuantity() * product.getPrice();
            totalValue += productValue;
            System.out.println(product + ", Total Value: $" + productValue);
        }
        System.out.println("Total Inventory Value: $" + totalValue);
    }

    private static void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            System.out.println("Inventory saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static void loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory = (Map<Integer, Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous inventory found.");
        }
    }
}
