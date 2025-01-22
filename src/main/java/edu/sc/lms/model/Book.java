package edu.sc.lms.model;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    private String bookId;
    private String bookTitle;
    private String isbn;
    private Double price;
    private String availability;
    private String bookCoverImg;
    private String category;
    private String authorName;
    @Setter
    private JFXButton updateBook;
    private JFXButton deleteBook;

}
