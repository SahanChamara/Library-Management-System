package edu.sc.lms.controller.dashboard;

import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.controller.bookcard.BookCardFormController;
import edu.sc.lms.dto.Book;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.DashBoardService;
import edu.sc.lms.util.ServiceType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DashboardFormController implements Initializable {

    @FXML
    public AnchorPane anchorPaneBookManagement;
    @FXML
    public AnchorPane anchorPaneDashboard;
    @FXML
    public AnchorPane mainAnchorPane;
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


    DashBoardService dashBoardService = ServiceFactory.getInstance().getServiceType(ServiceType.DASHBOARD);

    @FXML
    void btnBookManagementOnAction(ActionEvent event) throws IOException {
        loadPane("/view/book_manage_form.fxml", anchorPaneDashboard);
    }

    @FXML
    void btnCirculationOnAction(ActionEvent event) throws IOException {
        loadPane("/view/circulation_management_form.fxml", anchorPaneDashboard);

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        loadPane("/view/dashboard_form.fxml", anchorPaneDashboard);

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
        Stage currentStage = (Stage) anchorPaneDashboard.getScene().getWindow();
        if (currentStage != null) {
            currentStage.close();
        }

    }

    @FXML
    void btnMembersOnAction(ActionEvent event) throws IOException {
        loadPane("/view/member_management_form.fxml", anchorPaneDashboard);

    }

    @FXML
    void btnReportsOnAction(ActionEvent event) throws IOException {
        loadPane("/view/report_form.fxml", anchorPaneDashboard);

    }

    private List<Book> getData() {
        List<Book> bookCard = new ArrayList<>();
        Book book;

        for (Book bookDatabase : dashBoardService.loadBookToCard()) {
            book = new Book();
            book.setBookTitle(bookDatabase.getBookTitle());
            book.setPrice(bookDatabase.getPrice());
            book.setBookCoverImg(bookDatabase.getBookCoverImg());
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

            GridPane.setMargin(anchorPane, new Insets(20));
        }
    }

    void loadPane(String fxmlPath, AnchorPane pane) throws IOException {
        URL resource = this.getClass().getResource(fxmlPath);
        assert resource != null;
        Parent form = FXMLLoader.load(resource);
        pane.getChildren().clear();
        pane.getChildren().add(form);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTotalBooks.setText(dashBoardService.totalBooks());
        lblActiveMembers.setText(dashBoardService.activeMembers());
        lblBorrowedBooks.setText(dashBoardService.borrowedBooks());
        try {
            loadCard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
