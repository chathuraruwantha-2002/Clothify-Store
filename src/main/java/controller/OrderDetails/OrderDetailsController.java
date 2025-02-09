package controller.OrderDetails;

import DBConnection.DBConnection;
import controller.Products.ProductsController;
import model.Order;
import model.OrderItemsDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsController {

    public List<Order> GetAllOrderList () {

        List <Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()){
               Order order = new Order(
                       resultSet.getInt("OrderID"),
                       resultSet.getDouble("TotalAmount"),
                       resultSet.getDouble("Discount"),
                       resultSet.getDouble("Tax"),
                       resultSet.getString("IsReturned"),
                       resultSet.getString("PaymentType"),
                       resultSet.getString("Date"),
                       resultSet.getInt("UserID"),
                       resultSet.getInt("CustomerId")
               );
               orderList.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    public List <Order> SearchOrder (String input){
        List <Order> SearchResult = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE OrderID LIKE ? OR IsReturned LIKE ? OR PaymentType LIKE ? ";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Setting the search parameter for all columns
            String searchPattern = "%" + input + "%";
            for (int i = 1; i <= 3; i++) {
                preparedStatement.setString(i, searchPattern);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order(
                        resultSet.getInt("OrderID"),
                        resultSet.getDouble("TotalAmount"),
                        resultSet.getDouble("Discount"),
                        resultSet.getDouble("Tax"),
                        resultSet.getString("IsReturned"),
                        resultSet.getString("PaymentType"),
                        resultSet.getString("Date"),
                        resultSet.getInt("UserID"),
                        resultSet.getInt("CustomerId")
                );
                SearchResult.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return SearchResult;
    }

    public List<OrderItemsDetails> getItemList(int orderId) {
        List<OrderItemsDetails> itemList = new ArrayList<>();
        String query = "SELECT * FROM orderdetails WHERE OrderID = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItemsDetails item = new OrderItemsDetails(
                        resultSet.getInt("ProductID"),
                        new ProductsController().getProductNameById(resultSet.getInt("ProductID")),
                        resultSet.getInt("Qty"),
                        new ProductsController().getProductPriceById(resultSet.getInt("ProductID")),
                        resultSet.getInt("Qty")*new ProductsController().getProductPriceById(resultSet.getInt("ProductID"))
                );
                itemList.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }


    //additional methods

    //get Emp name using emp id
    public String getEmpName(int empId) {
        String query = "SELECT Name FROM employee WHERE EMPID = ?";
        String empName = null;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, empId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                empName = resultSet.getString("Name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empName;
    }

    //get Customer name using customer id
    public String getCustomerName(int customerId) {
        String query = "SELECT Name FROM customer WHERE CustomerID = ?";
        String customerName = null;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customerName = resultSet.getString("Name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerName;
    }

    public int getTotalReturns() {
        int totalReturns = 0;
        String query = "SELECT COUNT(*) AS TotalReturns FROM orders WHERE IsReturned = 'Yes'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalReturns = resultSet.getInt("TotalReturns");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalReturns;
    }



}
