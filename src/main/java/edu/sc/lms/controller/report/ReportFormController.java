package edu.sc.lms.controller.report;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {

    @FXML
    public LineChart lineChart;
    @FXML
    private Label lblActiveMember;

    @FXML
    private Label lblOverdueRate;

    @FXML
    private Label lblTotalCirculation;

    @FXML
    private Label lblTotalFine;

    @FXML
    private CategoryAxis lineChartMonthlyCirculation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series series = new XYChart.Series();
        series.setName("Borrowings");

        series.getData().add(new XYChart.Data("Monday",20));
        series.getData().add(new XYChart.Data("Tuesday",10));
        series.getData().add(new XYChart.Data("Wednesday",14));
        series.getData().add(new XYChart.Data("Thursday",12));
        series.getData().add(new XYChart.Data("Friday",32));

        lineChart.getData().add(series);

    }
}
