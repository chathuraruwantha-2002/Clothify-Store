package controller.Dashboard;

import DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DashboardController {

    public void dashbordChartDataLoad(){
        //get top 6 products sold today
        String query = "";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dashbordLineChartDataLoad(){

    }
}
