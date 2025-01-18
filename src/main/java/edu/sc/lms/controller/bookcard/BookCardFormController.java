package edu.sc.lms.controller.bookcard;

import edu.sc.lms.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookCardFormController {

    @FXML
    private ImageView coverImg;

    @FXML
    private Label lblBookTtle;

    @FXML
    private Label lblPrice;

    private Book book;

    public void setData(Book book){
        this.book=book;
        lblBookTtle.setText(book.getBookTitle());
        lblPrice.setText(String.valueOf(book.getPrice()));
        Image image = book.getBookCoverImg();
        coverImg.setImage(image);
    }

}
