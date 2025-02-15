package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Book;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.BookManageService;
import edu.sc.lms.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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

    ObservableList<Book> bookObservableList;

    BookManageService bookManageService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOKMANAGE);

    @FXML
    void btnAddNewBookOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/book_adding_form.fxml"))));
        stage.show();
    }

    public void loadBookData() {
        bookObservableList = FXCollections.observableArrayList();

        for (Book loadBookDetail : bookManageService.loadBookDetails()) {
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
            loadBookDetail.getDeleteBook().setOnAction(actionEvent -> {
                        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete it?..", ButtonType.YES, ButtonType.NO).showAndWait();
                        ButtonType buttonType = result.orElse(ButtonType.NO);
                        if (buttonType == ButtonType.YES) {
                            if (bookManageService.deleteBook(loadBookDetail.getBookId())) {
                                new Alert(Alert.AlertType.INFORMATION, "Book Delete Successful").show();
                                loadBookData();
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, "Book Delete Failed").show();
                            }
                        }
                    }
            );
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
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availableQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUpdateActions.setCellValueFactory(new PropertyValueFactory<>("updateBook"));
        colDeleteAction.setCellValueFactory(new PropertyValueFactory<>("deleteBook"));

        loadBookData();
    }

    @FXML
    public void txtSearchOnAction(ActionEvent actionEvent) {
        Book book = bookManageService.searchBook(new Book(null, txtSearch.getText(), txtSearch.getText(), 0.0, null, null, null, null, null, null, null, null));

        book.getUpdateBook().setOnAction(actionEvent2 -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/book_update_form.fxml"));
                Parent load = fxmlLoader.load();
                BookUpdateFormController controller = fxmlLoader.getController();
                controller.setSelectedBookId(book.getBookId());
                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.show();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        });
        book.getDeleteBook().setOnAction(actionEvent2 -> {
                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete it?..", ButtonType.YES, ButtonType.NO).showAndWait();
                    ButtonType buttonType = result.orElse(ButtonType.NO);
                    if (buttonType == ButtonType.YES) {
                        if (bookManageService.deleteBook(book.getBookId())) {
                            new Alert(Alert.AlertType.INFORMATION, "Book Delete Successful").show();
                            loadBookData();
                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Book Delete Failed").show();
                        }
                    }
                }
        );


        ObservableList<Book> searchBook = FXCollections.observableArrayList();
        searchBook.add(book);
        tblBooks.setItems(searchBook);
    }

    public void btnGetBookReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/BookReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            //JasperExportManager.exportReportToPdfFile(jasperPrint,"Book_Report.pdf");
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
