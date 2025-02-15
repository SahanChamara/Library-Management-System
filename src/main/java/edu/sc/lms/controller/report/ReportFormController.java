package edu.sc.lms.controller.report;

import edu.sc.lms.dto.Report;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.ReportService;
import edu.sc.lms.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {


    ReportService reportService = ServiceFactory.getInstance().getServiceType(ServiceType.REPORT);

    @FXML
    public BarChart barChartMemberActivity;

    @FXML
    public LineChart lineChart;

    @FXML
    public LineChart lineChartFineCollection;

    @FXML
    public PieChart pieChartPopularCategory;

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

    void weeklyCirculationChart() {
        XYChart.Series series = new XYChart.Series();
        series.setName("Borrowings");

        for (Report report : reportService.getWeeklyCirculation()) {
            series.getData().add(new XYChart.Data(report.getDayOfWeek(), report.getBookBorowedCount()));
        }
        lineChart.getData().add(series);
    }

    void popularCategoryPieChart() {
        for (Report popularBookCategory : reportService.getPopularBookCategoriesByBorrow()) {
            ObservableList<PieChart.Data> pieChartList = FXCollections.observableArrayList(
                    new PieChart.Data(popularBookCategory.getBookCategory(), popularBookCategory.getBorrowedCountAnBookCategory())
            );
            pieChartPopularCategory.getData().addAll(pieChartList);
        }
    }

    void activeMemberbarChart(){
        XYChart.Series series = new XYChart.Series();
        series.setName("Active Members An Week");

        for(Report activeMembers : reportService.getActiveMembers()){
            series.getData().add(new XYChart.Data(activeMembers.getDayOfWeek(),activeMembers.getActiveMemberCount()));
        }
        barChartMemberActivity.getData().add(series);
    }

    void fineColection(){
        XYChart.Series series = new XYChart.Series();
        series.setName("Fine Collection (LKR)");

        for (Report fineCollection : reportService.getFineCollection()) {
            series.getData().add(new XYChart.Data(fineCollection.getDayOfWeek(),fineCollection.getTotalFineCollection()));
        }
        lineChartFineCollection.getData().add(series);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTotalFine.setText(reportService.getTotalFine());
        lblTotalCirculation.setText(String.valueOf(reportService.getTotalCirculations()));
        weeklyCirculationChart();
        popularCategoryPieChart();
        activeMemberbarChart();
        fineColection();
    }
}
