package controller.Products;

import DBConnection.DBConnection;

import java.sql.Connection;
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
}
