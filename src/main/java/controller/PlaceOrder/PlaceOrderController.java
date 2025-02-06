package controller.PlaceOrder;

import DBConnection.DBConnection;
import model.Order;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderController {

    public String getCustomerName(String customerPhno) {
        String query = "SELECT Name FROM customer WHERE Phno = ?;";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerPhno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int getTopOrderID() {
        String query = "SELECT MAX(OrderID) AS TopOrderID FROM orders;";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("TopOrderID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getCustomerID(String customerPhno) {
        String query = "SELECT CustomerID FROM customer WHERE Phno = ?;";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerPhno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("CustomerID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean placeOrder(Order order, List<Product> productList) {
        String query = "INSERT INTO orders (OrderID, TotalAmount, Discount, Tax, IsReturned, PaymentType, Date, UserID, CustomerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setDouble(2, order.getSubTotal());
            preparedStatement.setDouble(3, order.getDiscount());
            preparedStatement.setDouble(4, order.getTax());
            preparedStatement.setString(5, order.getIsReturned());
            preparedStatement.setString(6, order.getPaymentType());
            preparedStatement.setString(7, order.getDate());
            preparedStatement.setInt(8, order.getEmpId());
            preparedStatement.setInt(9, order.getCustId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
