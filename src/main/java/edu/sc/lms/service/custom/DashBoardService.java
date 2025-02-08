package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Book;
import edu.sc.lms.entity.BookEntity;
import edu.sc.lms.service.SuperService;

import java.util.List;

public interface DashBoardService extends SuperService {
    String totalBooks();
    String activeMembers();
    String borrowedBooks();
    List<Book> loadBookToCard();
}
