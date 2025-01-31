package controller.OrderDetails;

import DBConnection.DBConnection;
import model.Order;

import java.sql.Connection;
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
                       resultSet.getInt(3),
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



}
