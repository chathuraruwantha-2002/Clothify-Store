package controller.PlaceOrder;

import DBConnection.DBConnection;
import controller.Inventory.InventoryController;
import controller.Products.ProductsController;
import model.Order;
import model.OrderItemsDetails;
import model.Product;

import java.sql.*;
import java.time.LocalDateTime;
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

    public boolean placeOrder(Order order, List<Product> productList) throws SQLException {
        String query = "INSERT INTO orders (OrderID, TotalAmount, Discount, Tax, IsReturned, PaymentType, Date, UserID, CustomerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setDouble(2, order.getSubTotal());
            preparedStatement.setDouble(3, order.getDiscount());
            preparedStatement.setDouble(4, order.getTax());
            preparedStatement.setString(5, order.getIsReturned());
            preparedStatement.setString(6, order.getPaymentType());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(8, order.getEmpId());
            preparedStatement.setInt(9, order.getCustId());
            boolean isInserted = preparedStatement.executeUpdate() > 0;

            if (isInserted) {
                boolean ItemsIsInsertedandUpdated = addOrderItemsDetails(order.getOrderId(), productList);
                if (ItemsIsInsertedandUpdated){
                    return true;
                }
            }
        } finally {
            connection.setAutoCommit(true);
        }
        connection.rollback();
        return false;
    }

    public boolean addOrderItemsDetails(int orderId, List<Product> productList){
        for (Product orderedProduct : productList) {
            OrderItemsDetails orderItemDetails = new OrderItemsDetails(
                    orderedProduct.getProductID(),
                    orderedProduct.getName(),
                    orderedProduct.getQtyBuying(),
                    orderedProduct.getPrice(),
                    orderedProduct.getTotalQtyPrice()
            );
            boolean isInserted = addOrderItemsDetails(orderId, orderItemDetails);
            boolean isUpdated = new InventoryController().updateInventory(orderedProduct);
            if(!isInserted && !isUpdated){
                return false;
            }
        }
        return true;
    }

    public boolean addOrderItemsDetails(int orderId, OrderItemsDetails orderItemDetails){
        String query = "INSERT INTO orderdetails (OrderID, ProductID, Price, Qty) VALUES (?, ?, ?, ?);";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,orderId);
            preparedStatement.setInt(2,orderItemDetails.getProductId());
            preparedStatement.setDouble(3,orderItemDetails.getTotalPrice());
            preparedStatement.setInt(4,orderItemDetails.getQty());
            boolean isInserted = preparedStatement.executeUpdate() > 0;
            if(isInserted){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
