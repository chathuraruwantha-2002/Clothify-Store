package controller.Inventory;

import DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {

    public List<Inventory> getAllCombinedProductData() {
        List<Inventory> combinedProductDataList = new ArrayList<>();

        String query = "SELECT " +
                "p.ProductID, " +
                "p.Name AS ProductName, " +
                "p.SupplierID, " +
                "c.Name AS CategoryName, " +
                "i.Qty AS QuantityInStock, " +
                "i.LastUpdate AS LastRestockedDate " +
                "FROM Product p " +
                "JOIN Category c ON p.CategoryID = c.CategoryID " +
                "JOIN Inventory i ON p.InventoryID = i.InventoryID;";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Inventory data = new Inventory(
                        resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getInt("SupplierID"),
                        resultSet.getString("CategoryName"),
                        resultSet.getInt("QuantityInStock"),
                        resultSet.getString("LastRestockedDate")
                );
                combinedProductDataList.add(data);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return combinedProductDataList;
    }
}
