package edu.sc.lms.model;

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
    private String availability;
    private String categoryId;
    private String authorId;
}
