package edu.sc.lms.controller.dashboard;

import edu.sc.lms.model.Book;

import java.util.List;

public interface DashBoardService {
    String totalBooks();
    String activeMembers();
    String borrowedBooks();
    List<Book> loadBookToCard();
}
