package edu.sc.lms.controller.dashboard;

import edu.sc.lms.controller.bookcard.BookCardFormController;
import edu.sc.lms.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane tabAnchorPane;

    private List<Book> bookCard = new ArrayList<>();

    private List<Book> getData(){
        List<Book> bookCard = new ArrayList<>();
        Book book;
        for(int i=0; i<20; i++){
            book = new Book();
            book.setBookTitle("Foundation");
            book.setPrice(1500.00);
            book.setBookCoverImg("/assets/book cover.png");
            bookCard.add(book);
        }
        return bookCard;
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookCard.addAll(getData());
        int column = 0;
        int row = 1;

        for(int i=0; i<bookCard.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/book_card__view.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            BookCardFormController bookCardFormController = fxmlLoader.getController();
            bookCardFormController.setData(bookCard.get(i));

            if(column==5){
                column=0;
                row++;
            }
            grid.add(anchorPane,column++,row);

            // setting the grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);


            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane,new Insets(5));
        }
    }
}
