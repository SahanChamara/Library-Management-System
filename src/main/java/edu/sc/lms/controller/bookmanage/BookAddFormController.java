package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookAddFormController implements Initializable {

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
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtNewCategory;

    private ObservableList<String> categoryList;
    private String imgpath;

    BookManageService bookManageService = ServiceFactory.getInstanace().getServiceType(ServiceType.BOOKMANAGE);

    @FXML
    void btnAddBookOnAction(ActionEvent event) throws IOException {
        if(bookManageService.addBook(new Book(null,
                txtBookTitle.getText(),
                txtIsbn.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtBookQty.getText()),
                imgpath,
                comboCategory.getSelectionModel().getSelectedItem().toString(),
                txtAuthor.getText(),null,null,null,null))){

            new Alert(Alert.AlertType.INFORMATION,"Book Added Successful").show();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/book_manage_form.fxml"));
            Parent load = fxmlLoader.load();
            BookManageFormController controller = fxmlLoader.getController();
            controller.loadBookData();
        }
    }

    @FXML
    void btnAddBookCoverImageOnAction(ActionEvent event) {
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
    void comboCategoryOnStateChange(ActionEvent event) {

        System.out.println(comboCategory.getSelectionModel().getSelectedItem().toString());

    }

    @FXML
    void comboAvailabilityOnStateChange(ActionEvent event) {

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
