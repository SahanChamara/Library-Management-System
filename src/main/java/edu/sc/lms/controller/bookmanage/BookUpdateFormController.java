package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class BookUpdateFormController implements Initializable {

    @FXML
    private JFXComboBox<?> comboCategory;

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

    @FXML
    void btnEditBookCoverImageOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditBookOnAction(ActionEvent event) {
        System.out.println(selectedBookId);

    }

    @FXML
    void comboCategoryOnStateChange(ActionEvent event) {

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
            Book book = BookManageController.getInstance().loadSelectedBook(selectedBookId);

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
