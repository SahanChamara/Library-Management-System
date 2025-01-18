package edu.sc.lms.controller.dashboard;

import edu.sc.lms.model.Book;

public interface DashBoardService {
    String totalBooks();
    String activeMembers();
    String borrowedBooks();
}
