package edu.sc.lms.repository.custom;

import edu.sc.lms.entity.BookEntity;
import edu.sc.lms.repository.CrudDao;
import java.util.List;

public interface DashboardDao extends CrudDao<BookEntity, String> {
    String totalBooks();

    String activeMembers();

    String borrowedBooks();

    List<BookEntity> loadBookToCard();

}
