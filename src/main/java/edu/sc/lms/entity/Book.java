package edu.sc.lms.entity;

import com.jfoenix.controls.JFXButton;
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
    private Integer availableQty;
    private String bookCoverImg;
    private String category;
    private String authorName;
    @Setter
    private JFXButton updateBook;
    private JFXButton deleteBook;
    private String categoryId;
    private String authorId;

}
