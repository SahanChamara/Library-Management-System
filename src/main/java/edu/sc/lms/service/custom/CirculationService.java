package edu.sc.lms.service.custom;
import edu.sc.lms.dto.BookRecord;
import edu.sc.lms.service.SuperService;

import java.util.List;

public interface CirculationService extends SuperService {
    List<String> loadMemberNames();
    List<String> loadBookTitle();
    boolean issueBook(BookRecord bookRecord);
    List<BookRecord> loadTable();
    BookRecord loadReturnDetails(String memberName,String bookTitle);
    List<String> loadBookTitleRe(String memberName);
    void calculateFine();
    Integer borrowedBook();
    Integer returnedBookCount();
    boolean returnBook(BookRecord bookRecord);

}
