package edu.sc.lms.service.custom.impl;

import com.google.inject.Inject;
import edu.sc.lms.dto.Author;
import edu.sc.lms.dto.Book;
import edu.sc.lms.dto.Category;
import edu.sc.lms.entity.BookEntity;
import edu.sc.lms.repository.DaoFactory;
import edu.sc.lms.repository.custom.BookManageDao;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.BookManageService;
import edu.sc.lms.util.DaoType;
import edu.sc.lms.util.ServiceType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManageServiceImpl implements BookManageService {

    BookManageDao bookManageDao = DaoFactory.getInstance().getDaoType(DaoType.BOOKMANAGE);
    ModelMapper mapper = new ModelMapper();

    @Override
    public List<Book> loadBookDetails() {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        for (BookEntity loadBookDetail : bookManageDao.loadBookDetails()) {
            bookArrayList.add(mapper.map(loadBookDetail,Book.class));
        }
        return bookArrayList;
        /*return bookManageDao.loadBookDetails()
                .stream()
                .map(bookEntity -> mapper.map(bookEntity, Book.class))
                .collect(Collectors.toList());*/
    }

    @Override
    public boolean addAuthor(Author author) {
        return false;
    }

    @Override
    public boolean addCategory(Category category) {
        return false;
    }

    @Override
    public boolean addBook(Book book) {
        return bookManageDao.add(mapper.map(book, BookEntity.class));
    }

    @Override
    public boolean deleteBook(String id) {
        return bookManageDao.delete(id);
    }

    @Override
    public boolean updateBook(Book book) {
        return bookManageDao.update(mapper.map(book, BookEntity.class));
    }

    @Override
    public Book searchBook(Book book) {
        return mapper.map(bookManageDao.search(mapper.map(book, BookEntity.class)), Book.class);
    }

    @Override
    public Book loadSelectedBook(String id) {
        return mapper.map(bookManageDao.loadSelectedBook(id), Book.class);
    }
}
