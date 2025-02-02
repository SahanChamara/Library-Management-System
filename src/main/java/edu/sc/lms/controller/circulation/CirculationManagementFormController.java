package edu.sc.lms.controller.circulation;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CirculationManagementFormController implements Initializable {

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
    private TableView<?> tblBookRecord;

    @FXML
    private JFXTextField txtFineAmount;

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {

    }

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {

    }

    void loadMemberNames(){
        ObservableList<String> nameArrayList = FXCollections.observableArrayList();
        nameArrayList.addAll(CirculationController.getInstance().loadMemberNames());
        comboMemberName.setItems(nameArrayList);
    }

    void loadBookTitles(){
        ObservableList<String> bookTitles = FXCollections.observableArrayList();
        bookTitles.addAll(CirculationController.getInstance().loadBookTitle());
        comboBookTitle.setItems(bookTitles);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMemberNames();
        loadBookTitles();
    }
}
