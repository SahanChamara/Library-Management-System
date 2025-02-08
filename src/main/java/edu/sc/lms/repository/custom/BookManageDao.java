package edu.sc.lms.repository.custom;

import edu.sc.lms.entity.AuthorEntity;
import edu.sc.lms.entity.BookEntity;
import edu.sc.lms.entity.CategoryEntity;
import edu.sc.lms.repository.CrudDao;

import java.util.List;

public interface BookManageDao extends CrudDao<BookEntity,String> {
    BookEntity loadSelectedBook(String id);
    boolean addCategory(CategoryEntity categoryEntity);
    boolean addAuthor(AuthorEntity authorEntity);
    List<BookEntity> loadBookDetails();
    String generateBookId();
    String generateCategoryId();
    String generateAuthorId();
}
