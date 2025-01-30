package controller.Products;

import DBConnection.DBConnection;
import controller.Inventory.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsController {

    public List<Product> GetAllProducts()  {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT p.ProductID, p.Name, p.Size, p.Image, p.Price, p.CategoryID, p.UserID, p.InventoryID, p.SupplierID, " +
                    "c.Name AS CategoryName, s.Name AS SupplierName, i.Qty " +
                    "FROM Product p " +
                    "JOIN Category c ON p.CategoryID = c.CategoryID " +
                    "JOIN Supplier s ON p.SupplierID = s.SupplierID " +
                    "JOIN Inventory i ON p.InventoryID = i.InventoryID";

            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()){
                Product product = new Product(
                        resultSet.getInt("ProductID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Size"),
                        resultSet.getString("Image"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("CategoryID"),
                        resultSet.getInt("UserID"),
                        resultSet.getInt("InventoryID"),
                        resultSet.getInt("SupplierID"),
                        resultSet.getString("CategoryName"),
                        resultSet.getString("SupplierName"),
                        resultSet.getInt("Qty")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(productList);
        return productList;
    }

    public List<Product> search(String input) {
        List<Product> productList = new ArrayList<>();

        String query = "SELECT " +
                "p.ProductID, " +
                "p.Name AS ProductName, " +
                "p.Size AS ProductSize, " +
                "p.Image AS ProductImage, " +
                "p.Price AS ProductPrice, " +
                "p.CategoryID, " +
                "p.UserID, " +
                "p.InventoryID, " +
                "p.SupplierID, " +
                "c.Name AS CategoryName, " +
                "s.Name AS SupplierName, " +
                "i.Qty AS QuantityInStock " +
                "FROM Product p " +
                "JOIN Category c ON p.CategoryID = c.CategoryID " +
                "JOIN Supplier s ON p.SupplierID = s.SupplierID " +
                "JOIN Inventory i ON p.InventoryID = i.InventoryID " +
                "WHERE p.ProductID LIKE ? " +
                "OR p.Name LIKE ? " +
                "OR p.SupplierID LIKE ? " +
                "OR c.Name LIKE ?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Setting the search parameter for all columns
            String searchPattern = "%" + input + "%";
            for (int i = 1; i <= 4; i++) {
                preparedStatement.setString(i, searchPattern);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductSize"),
                        resultSet.getString("ProductImage"),
                        resultSet.getDouble("ProductPrice"),
                        resultSet.getInt("CategoryID"),
                        resultSet.getInt("UserID"),
                        resultSet.getInt("InventoryID"),
                        resultSet.getInt("SupplierID"),
                        resultSet.getString("CategoryName"),
                        resultSet.getString("SupplierName"),
                        resultSet.getInt("QuantityInStock")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }
    public boolean updateProductDetails(Product product) {
        // Queries for updating the respective tables
        String productUpdateQuery =
                "UPDATE Product SET " +
                "Name = ?, " +
                "Size = ?, " +
                "Image = ?, " +
                "Price = ?, " +
                "SupplierID = ?, " +
                "CategoryID = ? " +
                "WHERE ProductID = ?";

        String inventoryUpdateQuery = "UPDATE Inventory SET Qty = ?, LastUpdate = NOW() WHERE InventoryID = ?";

        try {
            // Get database connection
            Connection connection = DBConnection.getInstance().getConnection();

            // Begin transaction
            connection.setAutoCommit(false);

            // Update Product table
            try (PreparedStatement productStatement = connection.prepareStatement(productUpdateQuery)) {
                productStatement.setString(1, product.getName());
                productStatement.setString(2, product.getSize());
                productStatement.setString(3, product.getImageUrl());
                productStatement.setDouble(4, product.getPrice());
                productStatement.setInt(5, product.getSupplierId());
                productStatement.setDouble(6, product.getCategoryId());
                productStatement.setInt(7, product.getProductID());
                productStatement.executeUpdate();
            }

            // Update Inventory table
            try (PreparedStatement inventoryStatement = connection.prepareStatement(inventoryUpdateQuery)) {
                inventoryStatement.setInt(1, product.getQty());
                inventoryStatement.setInt(2, product.getInventoryId());
                inventoryStatement.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            System.out.println("Product details updated successfully for ProductID: " + product.getProductID());
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product details: " + product.getProductID(), e);
        } finally {
            try {
                // Reset auto-commit
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                throw new RuntimeException("Error resetting auto-commit", autoCommitEx);
            }
        }

        return true;
    }

    public boolean deleteProduct(Product product) {
        // Query to delete the product from the Product table
        String deleteProductQuery = "DELETE FROM Product WHERE ProductID = ?";

        // Query to delete the product's inventory from the Inventory table
        String deleteInventoryQuery = "DELETE FROM Inventory WHERE InventoryID = ?";

        try {
            // Get the database connection
            Connection connection = DBConnection.getInstance().getConnection();

            // Begin transaction
            connection.setAutoCommit(false);

            // Delete from Product table
            try (PreparedStatement productStatement = connection.prepareStatement(deleteProductQuery)) {
                productStatement.setInt(1, product.getProductID());
                productStatement.executeUpdate();
            }

            // Delete from Inventory table
            try (PreparedStatement inventoryStatement = connection.prepareStatement(deleteInventoryQuery)) {
                inventoryStatement.setInt(1, product.getInventoryId());
                inventoryStatement.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            System.out.println("Product deleted successfully for ProductID: " + product.getProductID());
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product: " + product.getProductID(), e);
        } finally {
            try {
                // Reset auto-commit
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                throw new RuntimeException("Error resetting auto-commit", autoCommitEx);
            }
        }
        return true;
    }

    public boolean addProduct(Product product){
        try {
            String query = "INSERT INTO Product (Name, Size, Image, Price, CategoryID,  UserID, InventoryID, SupplierID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getSize());
            preparedStatement.setString(3, product.getImageUrl());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getCategoryId());
            preparedStatement.setInt(6, product.getUserId());
            preparedStatement.setInt(7, product.getProductID());
            preparedStatement.setInt(8, product.getSupplierId());


            if (preparedStatement.executeUpdate() > 0) {
                boolean result = addProductInventory(product);
                if (result) {
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //add product inventory
    public boolean addProductInventory(Product product){
        try {
            String query = "INSERT INTO Inventory (Qty, LastUpdate) VALUES (?, NOW())";
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, product.getQty());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getLastProductID() {
        try {

            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT MAX(ProductID) FROM Product";
            ResultSet resultSet = connection.createStatement().executeQuery(query);


            if (resultSet.next()) {
                int lastProductID = resultSet.getInt(1);
                System.out.println("Last inserted product ID: " + lastProductID);
                return lastProductID;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting last inserted product ID", e);
        }
        return 0;
    }


    public List<String> getAllSupplierNames() {
        List<String> supplierNames = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT Name FROM Supplier";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                supplierNames.add(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting supplier names", e);
        }
        return supplierNames;
    }

    //find supplier id by name
    public int getSupplierIdByName(String supplierName) {
        int supplierId = 0;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT SupplierID FROM Supplier WHERE Name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplierName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                supplierId = resultSet.getInt("SupplierID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting supplier ID by name", e);
        }
        return supplierId;
    }




}
