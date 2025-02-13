package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.dto.Book;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.BookManageService;
import edu.sc.lms.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BookUpdateFormController implements Initializable {

    public JFXTextField txtBookQty;
    @FXML
    private JFXComboBox comboCategory;

    @FXML
    private ImageView imgBookCover;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private JFXTextField txtIsbn;

    @FXML
    private JFXTextField txtNewCategory;

    @FXML
    private JFXTextField txtPrice;

    public String selectedBookId;
    private String imgpath;

    BookManageService bookManageService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOKMANAGE);

    @FXML
    void btnEditBookCoverImageOnAction(ActionEvent event) {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Insert an Book Cover Image");
        fileChooser.setInitialDirectory(new File("D:\\Lectures and Assignments ICET\\JavaFX\\Final Project\\Libraray Management System\\Project File\\Library-Management-System\\src\\main\\resources\\assets\\Cover Images"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG image", "*.png"), new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imgBookCover.setImage(new Image(selectedFile.getAbsolutePath()));
            imgpath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("no file selected");
        }

    }

    @FXML
    void btnEditBookOnAction(ActionEvent event) {
        if(bookManageService.updateBook(new Book(selectedBookId,
                txtBookTitle.getText(),
                txtIsbn.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtBookQty.getText()),
                imgpath,txtNewCategory.getText(),
                txtAuthor.getText(),
                null,null,null,null))){
            new Alert(Alert.AlertType.INFORMATION,"Book Updated Successful").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Book Updated Failed").show();
        }
    }

    @FXML
    void comboCategoryOnStateChange(ActionEvent event) {

    }

    @FXML
    void comboAvailabilityOnStateChange(ActionEvent event) {

    }

    @FXML
    void txtNewCategoryOnAction(ActionEvent event) {

    }

    public void setSelectedBookId(String selectedBookId) {
        this.selectedBookId = selectedBookId;
        loadSelectBook();
    }

    private void loadSelectBook() {
        if (selectedBookId != null) {
            Book book = bookManageService.loadSelectedBook(selectedBookId);

            txtBookTitle.setText(book.getBookTitle());
            txtAuthor.setText(book.getAuthorName());
            txtIsbn.setText(book.getIsbn());
            txtPrice.setText(String.valueOf(book.getPrice()));
            txtNewCategory.setText(book.getCategory());
            imgBookCover.setImage(new Image(book.getBookCoverImg()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
