package controller.OrderDetails;

import DBConnection.DBConnection;
import model.Order;

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
                       resultSet.getInt(1),
                       resultSet.getDouble(2),
                       resultSet.getString(3),
                       resultSet.getString(4),
                       resultSet.getString(5),
                       resultSet.getInt(6),
                       resultSet.getInt(7)
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
                        resultSet.getInt(1),
                        resultSet.getDouble(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7)
                );
                SearchResult.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return SearchResult;
    }



}
