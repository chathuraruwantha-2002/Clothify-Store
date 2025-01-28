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

}
