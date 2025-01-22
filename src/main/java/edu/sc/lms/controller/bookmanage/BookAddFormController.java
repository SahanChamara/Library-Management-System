package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BookAddFormController implements Initializable {

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
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtNewCategory;

    private ObservableList<String> categoryList;
    private String imgpath;

    @FXML
    void btnAddBookOnAction(ActionEvent event) {
        if(BookManageController.getInstance().addBook(new Book(null,
                txtBookTitle.getText(),
                txtIsbn.getText(),
                Double.parseDouble(txtPrice.getText()),
                "In Stock",
                imgpath,
                comboCategory.getSelectionModel().getSelectedItem().toString(),
                txtAuthor.getText(),null,null,null,null))){

            new Alert(Alert.AlertType.INFORMATION,"Book Added Successful").show();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/book_manage_form.fxml"));
            BookManageFormController controller = fxmlLoader.getController();
            controller.loadBookData();
        }
    }

    @FXML
    void btnAddBookCoverImageOnAction(ActionEvent event) {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Insert an Book Cover Image");
        fileChooser.setInitialDirectory(new File("C:\\Users\\Sahan Chamara\\Pictures"));
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
    void comboCategoryOnStateChange(ActionEvent event) {

        System.out.println(comboCategory.getSelectionModel().getSelectedItem().toString());

    }

    @FXML
    void txtNewCategoryOnAction(ActionEvent event) {
        categoryList.add(txtNewCategory.getText());
        loadCategories();
    }

    void loadCategories() {
        categoryList = FXCollections.observableArrayList();
        categoryList.addAll("Fiction",
                "Non-Fiction",
                "Science Fiction",
                "Fantasy",
                "Biography",
                "History",
                "Mystery",
                "Thriller",
                "Self-Help",
                "Health & Wellness",
                "Education",
                "Children's Books",
                "Young Adult",
                "Travel",
                "Cooking",
                "Poetry",
                "Graphic Novels",
                "Art & Photography",
                "Religion & Spirituality",
                "Business & Economics",
                "Technology",
                "Science",
                "Philosophy",
                "Sports & Fitness",
                "Music");
        comboCategory.setItems(categoryList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategories();
    }
}