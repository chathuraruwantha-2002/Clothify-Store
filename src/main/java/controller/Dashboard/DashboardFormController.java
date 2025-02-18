package controller.Dashboard;

import controller.Inventory.InventoryController;
import controller.OrderDetails.OrderDetailsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.time.LocalDate;
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

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label date;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showData();
        initializeBarChart();
        initializeLineChart();
        displayCalendar(LocalDate.now());
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

    private void displayCalendar(LocalDate currentDate) {
        date.setText(currentDate.toString());

        int dayOfWeek = currentDate.withDayOfMonth(1).getDayOfWeek().getValue();
        int daysInMonth = currentDate.getMonth().length(currentDate.isLeapYear());


        calendarGrid.getChildren().clear();


        String[] weekdays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < 7; i++) {
            Label weekdayLabel = new Label(weekdays[i]);
            weekdayLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: center;");
            weekdayLabel.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHalignment(weekdayLabel, HPos.CENTER);
            calendarGrid.add(weekdayLabel, i, 0);
        }


        int row = 1;
        int col = dayOfWeek - 1;

        for (int day = 1; day <= daysInMonth; day++) {
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-font-size: 13px; -fx-alignment: center;");
            dayLabel.setMaxWidth(Double.MAX_VALUE);
            dayLabel.setMaxHeight(Double.MAX_VALUE);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            GridPane.setValignment(dayLabel, VPos.CENTER);
            GridPane.setHgrow(dayLabel, Priority.ALWAYS);
            GridPane.setVgrow(dayLabel, Priority.ALWAYS);
            calendarGrid.add(dayLabel, col, row);

            //change color of currentdate
            if (day == currentDate.getDayOfMonth()) {
                dayLabel.setStyle("-fx-font-size: 19px; -fx-alignment: center; -fx-font-weight: bold; -fx-background-color: #ede0d4;");
            }

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }
}
