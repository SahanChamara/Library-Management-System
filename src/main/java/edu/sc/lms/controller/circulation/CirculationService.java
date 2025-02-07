package edu.sc.lms.controller.circulation;
import edu.sc.lms.model.BookRecord;

import java.util.List;

public interface CirculationService {
    List<String> loadMemberNames();
    List<String> loadBookTitle();
    String generaRecordId();
    String generateFineId();
    boolean issueBook(BookRecord bookRecord);
    List<BookRecord> loadTable();
}
