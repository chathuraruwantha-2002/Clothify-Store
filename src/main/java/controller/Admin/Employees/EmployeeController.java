package controller.Admin.Employees;

import DBConnection.DBConnection;
import model.Employee;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {


    public List<Employee> getAllEmployees() {
        List <Employee> employeeList = new ArrayList<>();
        String query = "SELECT *  FROM employee ;";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                Employee employee = new Employee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeList;
    }

    public List<Employee> searchEmployee(String employeeName){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT *  FROM employee  WHERE Name LIKE ? ;";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + employeeName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Employee employee = new Employee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    public int getTopEmployeeID() {
        String query = "SELECT MAX(EMPID) AS TopEMPID FROM employee;";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("TopEMPID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    //write transactions completely..!
    public boolean addEmployee(Employee employee, User user){
        String query = "INSERT INTO employee (EMPID, Name, Address, Contact, Email, UserID, Gender, Role) VALUES(?,?,?,?,?,?,?,?);";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employee.getEmployeeId());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getAddress());
            preparedStatement.setString(4, employee.getContact());
            preparedStatement.setString(5, employee.getEmail());
            preparedStatement.setInt(6, employee.getUserId());
            preparedStatement.setString(7, employee.getGender());
            preparedStatement.setString(8, employee.getRole());
            boolean isAdded = preparedStatement.executeUpdate() > 0;
            if(isAdded){
                boolean isAddedUser = addUser(user);
                if(isAddedUser){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean addUser(User user){
        String query = "INSERT INTO user (UserID, Email, Password, Role) VALUES(?,?,?,?);";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());
            boolean isAdded = preparedStatement.executeUpdate() > 0;
            if(isAdded){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }



}
