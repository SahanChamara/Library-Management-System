package edu.sc.lms.controller.bookmanage;

import edu.sc.lms.model.Book;

import java.util.List;

public interface BookManageService {
    List<Book> loadBookDetails();
}
