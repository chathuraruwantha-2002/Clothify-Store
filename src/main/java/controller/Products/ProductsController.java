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
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM product");

            while (resultSet.next()){
                Product product = new Product(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9)
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


