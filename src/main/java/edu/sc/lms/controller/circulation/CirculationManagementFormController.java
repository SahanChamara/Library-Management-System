package edu.sc.lms.controller.circulation;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.BookRecord;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.CirculationService;
import edu.sc.lms.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalTime;

public class CirculationManagementFormController implements Initializable {

    @FXML
    public JFXComboBox comboBookTitleRe;
    @FXML
    private TableColumn colBookTitle;

    @FXML
    private TableColumn colBorrowedDate;

    @FXML
    private TableColumn colDueDate;

    @FXML
    private TableColumn colFine;

    @FXML
    private TableColumn colMemberName;

    @FXML
    private TableColumn colReturnDate;

    @FXML
    private TableColumn colStatus;

    @FXML
    private JFXComboBox comboAllStatus;

    @FXML
    private JFXComboBox comboBookTitle;

    @FXML
    private JFXComboBox comboMemberName;

    @FXML
    private JFXComboBox comboMemberNameRe;

    @FXML
    private JFXComboBox comboSearchByMember;

    @FXML
    private DatePicker dateDueDate;

    @FXML
    private Label lblActiveBook;

    @FXML
    private Label lblBookBorrowed;

    @FXML
    private Label lblBookReturn;

    @FXML
    private Label lblBookTitle;

    @FXML
    private Label lblBorrowedDate;

    @FXML
    private Label lblDueDate;

    @FXML
    private Label lblFine;

    @FXML
    private Label lblOverdueBook;

    @FXML
    private TableView tblBookRecord;

    @FXML
    private JFXTextField txtFineAmount;

    CirculationService circulationService = ServiceFactory.getInstance().getServiceType(ServiceType.CIRCULATION);

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {
        if (circulationService.issueBook(new BookRecord(null,
                null,
                comboMemberName.getSelectionModel().getSelectedItem().toString(),
                null,
                comboBookTitle.getSelectionModel().getSelectedItem().toString(),
                LocalDate.now(),
                dateDueDate.getValue(),
                null,
                0,
                null,
                0.0)
        )) {
            new Alert(Alert.AlertType.INFORMATION, "Book Issued").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Book Issued Failed").show();
        }
    }

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {
        try {
            if (circulationService.returnBook(new BookRecord(null,
                    null,
                    comboMemberNameRe.getSelectionModel().getSelectedItem().toString(),
                    null,
                    comboBookTitleRe.getSelectionModel().getSelectedItem().toString(),
                    null,
                    null,
                    String.valueOf(LocalDate.now()),
                    1,
                    null,
                    Double.parseDouble(txtFineAmount.getText())))) {
                new Alert(Alert.AlertType.INFORMATION, "Book Returned Successful").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Book Returned Failed").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.INFORMATION,"Please Enter the Fine Amount").show();
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.INFORMATION,"Please Select the Member Name").show();
        }


    }

    void loadMemberNames() {
        ObservableList<String> nameArrayList = FXCollections.observableArrayList();
        nameArrayList.addAll(circulationService.loadMemberNames());
        comboMemberName.setItems(nameArrayList);
        comboMemberNameRe.setItems(nameArrayList);
    }

    void loadBookTitles() {
        ObservableList<String> bookTitles = FXCollections.observableArrayList();
        bookTitles.addAll(circulationService.loadBookTitle());
        comboBookTitle.setItems(bookTitles);
        //comboBookTitleRe.setItems(bookTitles);
    }

    void loadTable() {
        ObservableList<BookRecord> bookRecordObservableList = FXCollections.observableArrayList();
        bookRecordObservableList.addAll(circulationService.loadTable());
        tblBookRecord.setItems(bookRecordObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("dateGiven"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));

        loadMemberNames();
        loadBookTitles();
        loadTable();
        lblBookBorrowed.setText(String.valueOf(circulationService.borrowedBook()));
        lblBookReturn.setText(String.valueOf(circulationService.returnedBookCount()));
        //CirculationController.getInstance().calculateFine();
        circulationService.calculateFine();
        //methodInvokeAtOnce();
    }

    @FXML
    public void selectReturnBookOnAction(Event Event) {
        try {
            String selectedBook = comboBookTitleRe.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            return;
        }
        try {
            BookRecord bookRecord = circulationService.loadReturnDetails(comboMemberNameRe.getSelectionModel().getSelectedItem().toString(), comboBookTitleRe.getSelectionModel().getSelectedItem().toString());
            if (bookRecord != null) {
                lblBorrowedDate.setText(String.valueOf(bookRecord.getBorrowedDate()));
                lblDueDate.setText(String.valueOf(bookRecord.getReturnDate()));
                lblFine.setText(String.valueOf(bookRecord.getFineAmount()));

                if(lblFine.getText().equals("0.0")){
                    txtFineAmount.setDisable(true);
                    txtFineAmount.setText("0.0");
                }else {
                    txtFineAmount.setDisable(false);
                }
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("Error");
        }
    }

    @FXML
    public void selectReturnMemberNameOnAction(Event Event) {
        comboBookTitleRe.getSelectionModel().clearSelection();
        ObservableList<String> bookTitleObservableList = FXCollections.observableArrayList();
        bookTitleObservableList.addAll(circulationService.loadBookTitleRe(comboMemberNameRe.getSelectionModel().getSelectedItem().toString()));
        comboBookTitleRe.setItems(bookTitleObservableList);
    }

    // method invoke daily at once
    public void methodInvokeAtOnce() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> circulationService.calculateFine();
        long initialDelay = getInitialDelay(8, 0);
        long period = 24 * 60;
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MINUTES);
    }

    private static long getInitialDelay(int targetHour, int targetMinute) {
        LocalTime now = LocalTime.now();
        LocalTime targetTime = LocalTime.of(targetHour, targetMinute);

        long delayMinutes = now.until(targetTime, java.time.temporal.ChronoUnit.MINUTES);
        return delayMinutes < 0 ? delayMinutes + 1440 : delayMinutes;
    }


    public void btnGetCirculationReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/Circulation.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            //JasperExportManager.exportReportToPdfFile(jasperPrint,"Book_Report.pdf");
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
