package edu.sc.lms.service.custom;
import edu.sc.lms.dto.BookRecord;

import java.util.List;

public interface CirculationService {
    List<String> loadMemberNames();
    List<String> loadBookTitle();
    String generaRecordId();
    String generateFineId();
    boolean issueBook(BookRecord bookRecord);
    List<BookRecord> loadTable();
    BookRecord loadReturnDetails(String memberName,String bookTitle);
    List<String> loadBookTitleRe(String memberName);
    void calculateFine();

}
