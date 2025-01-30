package controller.Suppliers;

import DBConnection.DBConnection;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersController {


    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT * FROM Supplier";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                int supplierId = resultSet.getInt("SupplierID");
                int userId = resultSet.getInt("UserID");
                String gender = resultSet.getString("Gender");
                String name = resultSet.getString("Name");
                String company = resultSet.getString("Company");
                String address = resultSet.getString("Address");
                String phone = resultSet.getString("Contact");
                String email = resultSet.getString("Email");
                Supplier supplier = new Supplier(supplierId, userId, gender, name, company, address, phone, email);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting suppliers", e);
        }
        return suppliers;
    }

    public List<String> getAllSupplierNames() {
        List<String> supplierNames = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT Name FROM Supplier";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                supplierNames.add(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting supplier names", e);
        }
        return supplierNames;
    }

    public int getSupplierIdByName(String supplierName) {
        int supplierId = 0;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT SupplierID FROM Supplier WHERE Name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplierName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                supplierId = resultSet.getInt("SupplierID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting supplier ID by name", e);
        }
        return supplierId;
    }

    public boolean updateSupplier(Supplier supplier){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "UPDATE Supplier SET  Name = ?, Gender = ?, Company = ?, Address = ?, Contact = ?, Email = ? WHERE SupplierID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getGender());
            preparedStatement.setString(3, supplier.getCompany());
            preparedStatement.setString(4, supplier.getAddress());
            preparedStatement.setString(5, supplier.getPhone());
            preparedStatement.setString(6, supplier.getEmail());
            preparedStatement.setInt(7, supplier.getSupplierId());
            boolean result = preparedStatement.executeUpdate()>0;
            if(result){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean deleteSupplier(int supplierId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "DELETE FROM Supplier WHERE SupplierID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, supplierId);
            boolean result = preparedStatement.executeUpdate() > 0;
            if (result) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
