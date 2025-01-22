package edu.sc.lms.controller.bookmanage;

import edu.sc.lms.model.Author;
import edu.sc.lms.model.Book;
import edu.sc.lms.model.Category;

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
    boolean updateBook(String id);
}
