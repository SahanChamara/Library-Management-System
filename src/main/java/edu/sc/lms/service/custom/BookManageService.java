package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Author;
import edu.sc.lms.dto.Book;
import edu.sc.lms.dto.Category;

import java.util.List;

public interface BookManageService {
    List<Book> loadBookDetails();
    boolean addAuthor(Author author);
    boolean addCategory(Category category);
    boolean addBook(Book book);
    String generateAuthorId();
    String generateCategoryId();
    String generateBookId();
    boolean deleteBook(String id);
    boolean updateBook(Book book);
    Book searchBook(Book book);
    Book loadSelectedBook(String id);
}
