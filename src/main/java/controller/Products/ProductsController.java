package controller.Products;

import DBConnection.DBConnection;
import controller.Inventory.InventoryController;
import model.Product;

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
                    "c.Name AS CategoryName, COALESCE(s.Name, 'Supplier Deleted') AS SupplierName, i.Qty " +
                    "FROM Product p " +
                    "JOIN Category c ON p.CategoryID = c.CategoryID " +
                    "LEFT JOIN Supplier s ON p.SupplierID = s.SupplierID " +
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

    public List<Product> searchProduct(String input) {
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
                "COALESCE(s.Name, 'Supplier Deleted') AS SupplierName, " +
                "i.Qty AS QuantityInStock " +
                "FROM Product p " +
                "JOIN Category c ON p.CategoryID = c.CategoryID " +
                "LEFT JOIN Supplier s ON p.SupplierID = s.SupplierID " +
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

    //update product tansaction completely done
    public boolean updateProduct(Product product) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String productUpdateQuery =
                "UPDATE Product SET " +
                "Name = ?, " +
                "Size = ?, " +
                "Image = ?, " +
                "Price = ?, " +
                "SupplierID = ?, " +
                "CategoryID = ? " +
                "WHERE ProductID = ?";

        try {

            connection.setAutoCommit(false);

            PreparedStatement productStatement = connection.prepareStatement(productUpdateQuery);
            productStatement.setString(1, product.getName());
            productStatement.setString(2, product.getSize());
            productStatement.setString(3, product.getImageUrl());
            productStatement.setDouble(4, product.getPrice());
            productStatement.setInt(5, product.getSupplierId());
            productStatement.setDouble(6, product.getCategoryId());
            productStatement.setInt(7, product.getProductID());
            boolean isUpdated = productStatement.executeUpdate() > 0;

            if (isUpdated) {
                boolean isUpdatedInventory = new InventoryController().updateInventory(product);
                if (isUpdatedInventory) {
                    connection.commit();
                    return true;
                }
            }

        } finally {
            connection.setAutoCommit(true);
        }
        connection.rollback();
        return false;
    }



    //delete product transaction completely done
    public boolean deleteProduct(Product product) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String deleteProductQuery = "DELETE FROM Product WHERE ProductID = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement productStatement = connection.prepareStatement(deleteProductQuery);
            productStatement.setInt(1, product.getProductID());
            boolean isDeleted = productStatement.executeUpdate() > 0;

            if (isDeleted) {
                boolean isDeletedFromInventory = new InventoryController().deleteProductInventory(product.getInventoryId());
                if (isDeletedFromInventory) {
                    connection.commit();
                    return true;
                }
            }
        } finally {
            connection.setAutoCommit(true);
        }

        connection.rollback();
        return false;
    }



    //completely done with transactions
    public boolean addProduct(Product product) throws SQLException {
        String query = "INSERT INTO Product (Name, Size, Image, Price, CategoryID,  UserID, InventoryID, SupplierID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getSize());
            preparedStatement.setString(3, product.getImageUrl());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getCategoryId());
            preparedStatement.setInt(6, product.getUserId());
            preparedStatement.setInt(7, product.getProductID());
            preparedStatement.setInt(8, product.getSupplierId());
            boolean isInserted = preparedStatement.executeUpdate() > 0;

            if (isInserted) {
                boolean isInsertedInventory = new InventoryController().addProductInventory(product);
                if (isInsertedInventory) {
                    connection.commit();
                    return true;
                }
            }
        } finally {
            connection.setAutoCommit(true);
        }
        connection.rollback();
        return false;
    }

    //add product inventory
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


}
