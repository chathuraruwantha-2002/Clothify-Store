package controller.Dashboard;

import controller.Inventory.InventoryController;
import controller.OrderDetails.OrderDetailsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private Label activeLabel;

    @FXML
    private Label alertsLabel;

    @FXML
    private Label returnsLabel;

    @FXML
    private Label salesLabel;

    @FXML
    private BarChart<?, ?> topSellingBarChart;

    @FXML
    private LineChart<?, ?> salesLineChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showData();
        initializeBarChart();
        initializeLineChart();
    }
    private void showData() {
        returnsLabel.setText(String.valueOf(new OrderDetailsController().getTotalReturns()));
        alertsLabel.setText(String.valueOf(new InventoryController().getLowProductQty()));

    }

    private void initializeBarChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("T-Shirts", 120));
        series.getData().add(new XYChart.Data("Shoes", 100));
        series.getData().add(new XYChart.Data("Accessories", 90));
        series.getData().add(new XYChart.Data("Caps", 100));
        series.getData().add(new XYChart.Data("Belts", 80));
        series.getData().add(new XYChart.Data("Pants", 80));


        topSellingBarChart.getData().add(series);
    }

    private void initializeLineChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Jan", 100));
        series.getData().add(new XYChart.Data("Feb", 120));
        series.getData().add(new XYChart.Data("Mar", 90));
        series.getData().add(new XYChart.Data("Apr", 80));
        series.getData().add(new XYChart.Data("May", 70));
        series.getData().add(new XYChart.Data("Jun", 60));


        salesLineChart.getData().add(series);
    }
}
