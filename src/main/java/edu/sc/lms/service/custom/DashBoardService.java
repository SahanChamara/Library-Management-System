package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Book;

import java.util.List;

public interface DashBoardService {
    String totalBooks();
    String activeMembers();
    String borrowedBooks();
    List<Book> loadBookToCard();
}
