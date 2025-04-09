package controller.Admin.Reports;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsFormController implements Initializable {

    @FXML
    private LineChart<String, Number> chartAnnuallySales;

    @FXML
    private LineChart<String, Number> chartDailySales;

    @FXML
    private LineChart<String, Number> chartMonthlySales;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMonthlySalesData();
        loadDailySalesData();
        loadAnnuallySalesData();
    }

    private void loadMonthlySalesData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("2025");

        series.getData().add(new XYChart.Data<>("Jan", 1000));
        series.getData().add(new XYChart.Data<>("Feb", 1200));
        series.getData().add(new XYChart.Data<>("Mar", 900));
        series.getData().add(new XYChart.Data<>("Apr", 1500));
        series.getData().add(new XYChart.Data<>("May", 1300));
        series.getData().add(new XYChart.Data<>("Jun", 1700));
        series.getData().add(new XYChart.Data<>("Jul", 1600));

        chartMonthlySales.getData().add(series);
    }

    private void loadDailySalesData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Week 1");

        series.getData().add(new XYChart.Data<>("Mon", 200));
        series.getData().add(new XYChart.Data<>("Tue", 450));
        series.getData().add(new XYChart.Data<>("Wed", 300));
        series.getData().add(new XYChart.Data<>("Thu", 500));
        series.getData().add(new XYChart.Data<>("Fri", 400));
        series.getData().add(new XYChart.Data<>("Sat", 380));
        series.getData().add(new XYChart.Data<>("Sun", 420));

        chartDailySales.getData().add(series);
    }

    private void loadAnnuallySalesData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Annual Sales");

        series.getData().add(new XYChart.Data<>("2020", 12000));
        series.getData().add(new XYChart.Data<>("2021", 15000));
        series.getData().add(new XYChart.Data<>("2022", 18000));
        series.getData().add(new XYChart.Data<>("2023", 21000));
        series.getData().add(new XYChart.Data<>("2024", 25000));

        chartAnnuallySales.getData().add(series);
    }
}
