package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookManageFormController implements Initializable {

    @FXML
    private TableColumn colActions;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colAuthor;

    @FXML
    private TableColumn colBookId;

    @FXML
    private TableColumn colCategory;

    @FXML
    private TableColumn colIsbn;

    @FXML
    private TableColumn colAvailability;

    @FXML
    private TableColumn colTitle;

    @FXML
    private JFXComboBox comboSelectCategory;

    @FXML
    private JFXComboBox comboSelectStatus;

    @FXML
    private TableView tblBooks;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    void btnAddNewBookOnAction(ActionEvent event) {


    }

    private void loadBookData(){
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();

        bookObservableList.addAll(BookManageController.getInstance().loadBookDetails());
        tblBooks.setItems(bookObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colActions.setCellValueFactory(new PropertyValueFactory<>("updateBook"));

        loadBookData();
    }
}
