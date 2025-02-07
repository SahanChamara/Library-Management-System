package edu.sc.lms.controller.circulation;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.BookRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {
        if (CirculationController.getInstance().issueBook(new BookRecord(null,
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

    }

    void loadMemberNames() {
        ObservableList<String> nameArrayList = FXCollections.observableArrayList();
        nameArrayList.addAll(CirculationController.getInstance().loadMemberNames());
        comboMemberName.setItems(nameArrayList);
        comboMemberNameRe.setItems(nameArrayList);
    }

    void loadBookTitles() {
        ObservableList<String> bookTitles = FXCollections.observableArrayList();
        bookTitles.addAll(CirculationController.getInstance().loadBookTitle());
        comboBookTitle.setItems(bookTitles);
        //comboBookTitleRe.setItems(bookTitles);
    }

    void loadTable() {
        ObservableList<BookRecord> bookRecordObservableList = FXCollections.observableArrayList();
        bookRecordObservableList.addAll(CirculationController.getInstance().loadTable());
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
        //CirculationController.getInstance().calculateFine();
        methodInvokeAtOnce();
    }

    @FXML
    public void selectReturnBookOnAction(ActionEvent actionEvent) {
        BookRecord bookRecord = CirculationController.getInstance().loadReturnDetails(comboMemberNameRe.getSelectionModel().getSelectedItem().toString(), comboBookTitleRe.getSelectionModel().getSelectedItem().toString());
        lblBorrowedDate.setText(String.valueOf(bookRecord.getBorrowedDate()));
        lblDueDate.setText(String.valueOf(bookRecord.getReturnDate()));
        lblFine.setText(String.valueOf(bookRecord.getFineAmount()));
    }

    @FXML
    public void selectReturnMemberNameOnAction(ActionEvent actionEvent) {
        ObservableList<String> bookTitleObservableList = FXCollections.observableArrayList();
        bookTitleObservableList.addAll(CirculationController.getInstance().loadBookTitleRe(comboMemberNameRe.getSelectionModel().getSelectedItem().toString()));
        comboBookTitleRe.setItems(bookTitleObservableList);
    }

    // method invoke daily at once
    public static void methodInvokeAtOnce() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> CirculationController.getInstance().calculateFine();
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


}
