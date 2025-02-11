package edu.sc.lms.repository.custom;

import edu.sc.lms.dto.BookRecord;
import edu.sc.lms.entity.BookRecordEntity;
import edu.sc.lms.repository.CrudDao;

import java.util.List;

public interface CirculationDao extends CrudDao<BookRecordEntity,String> {
    List<String> loadMemberNames();
    List<String> loadBookTitle();
    String generaRecordId();
    String generateFineId();
    boolean issueBook(BookRecordEntity bookRecord);
    List<BookRecordEntity> loadTable();
    BookRecordEntity loadReturnDetails(String memberName,String bookTitle);
    List<String> loadBookTitleRe(String memberName);
    List<BookRecordEntity> returnFineCalculatingDetails();
    Integer borrowedBook();
    Integer returnedBookCount();
    boolean returnBook(BookRecordEntity bookRecordEntity);
    Integer updateCalculatedFine(Double calculatedFineAmount,String recordId);
}
