package edu.sc.lms.controller.report;

import edu.sc.lms.dto.Report;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.ReportService;
import edu.sc.lms.util.ServiceType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {
    ReportService reportService = ServiceFactory.getInstance().getServiceType(ServiceType.REPORT);

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

    void loadChart(){
        XYChart.Series series = new XYChart.Series();
        series.setName("Borrowings");

        for (Report report : reportService.getWeeklyCirculation()) {
            series.getData().add(new XYChart.Data(report.getDayOfWeek(),report.getBookBorowedCount()));
        }
        lineChart.getData().add(series);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChart();
    }
}
