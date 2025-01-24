package edu.sc.lms.controller.bookmanage;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookManageFormController implements Initializable {

    @FXML
    private TableColumn colDeleteAction;

    @FXML
    private TableColumn colUpdateActions;

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
    private JFXButton updateBtn;

    @FXML
    void btnAddNewBookOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/book_adding_form.fxml"))));
        stage.show();
    }

    public void loadBookData(){
        System.out.println("method call");
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();

        for (Book loadBookDetail : BookManageController.getInstance().loadBookDetails()) {
            loadBookDetail.getUpdateBook().setOnAction(actionEvent -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/book_update_form.fxml"));
                    Parent load = fxmlLoader.load();
                    BookUpdateFormController controller = fxmlLoader.getController();
                    controller.setSelectedBookId(loadBookDetail.getBookId());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(load));
                    stage.show();
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            });
            loadBookDetail.getDeleteBook().setOnAction(actionEvent -> System.out.println(loadBookDetail.getBookId()));
            bookObservableList.add(loadBookDetail);
        }
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
        colUpdateActions.setCellValueFactory(new PropertyValueFactory<>("updateBook"));
        colDeleteAction.setCellValueFactory(new PropertyValueFactory<>("deleteBook"));

        loadBookData();
    }
}
