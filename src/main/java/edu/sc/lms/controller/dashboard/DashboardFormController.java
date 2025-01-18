package edu.sc.lms.controller.dashboard;

import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.controller.bookcard.BookCardFormController;
import edu.sc.lms.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private TabPane TabPane;

    @FXML
    private JFXButton btnAddBookSecond;

    @FXML
    private JFXButton btnAddMemberSecond;

    @FXML
    private GridPane grid;

    @FXML
    private Label lblActiveMembers;

    @FXML
    private Label lblBorrowedBooks;

    @FXML
    private Label lblDueReturns;

    @FXML
    private Label lblTotalBooks;

    @FXML
    private Label lblViewUserName;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane tabAnchorPane;

    @FXML
    private Tab tabMostPopular;

    @FXML
    private Tab tabRecenltyAdded;

    @FXML
    private JFXTextField txtSearchBar;

    private final List<Book> bookCardArray = new ArrayList<>();

    private Stage dialogStage;

    @FXML
    void btnBookManagementOnAction(ActionEvent event) {

    }

    @FXML
    void btnCirculationOnAction(ActionEvent event) {

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    @FXML
    void btnMembersOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {

    }

    private List<Book> getData() {
        List<Book> bookCard = new ArrayList<>();
        Book book;
        for (int i = 0; i < 20; i++) {
            book = new Book();
            book.setBookTitle("Foundation");
            book.setPrice(1500.00);
            book.setBookCoverImg("/assets/book cover.png");
            bookCard.add(book);
        }
        return bookCard;
    }

    private void loadCard() throws IOException {
        bookCardArray.addAll(getData());
        int column = 0;
        int row = 1;

        for (Book book : bookCardArray) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/book_card__view.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            BookCardFormController bookCardFormController = fxmlLoader.getController();
            bookCardFormController.setData(book);

            if (column == 5) {
                column = 0;
                row++;
            }
            grid.add(anchorPane, column++, row);

            // setting the grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);


            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(5));
        }
    }
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCard();
    }

    public TabPane getTabPane() {
        return TabPane;
    }

    public void setTabPane(TabPane tabPane) {
        TabPane = tabPane;
    }
}
