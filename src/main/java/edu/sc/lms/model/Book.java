package edu.sc.lms.model;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String categoryId;
    private String authorId;

}
