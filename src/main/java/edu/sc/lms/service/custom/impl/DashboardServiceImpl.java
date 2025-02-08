package edu.sc.lms.service.custom.impl;

import com.google.inject.Inject;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Book;
import edu.sc.lms.entity.BookEntity;
import edu.sc.lms.repository.DaoFactory;
import edu.sc.lms.repository.custom.DashboardDao;
import edu.sc.lms.service.custom.DashBoardService;
import edu.sc.lms.util.DaoType;
import javafx.scene.image.Image;
import org.modelmapper.ModelMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardServiceImpl implements DashBoardService {
    private Image image;

    @Inject
    DashboardDao dashboardDao;
    ModelMapper mapper =  new ModelMapper();

    @Override
    public String totalBooks() {
        return dashboardDao.totalBooks();
    }

    @Override
    public String activeMembers() {
        return dashboardDao.activeMembers();
    }

    @Override
    public String borrowedBooks() {
        return dashboardDao.borrowedBooks();
    }

    @Override
    public List<Book> loadBookToCard() {
       return dashboardDao.loadBookToCard()
               .stream()
               .map(bookEntity -> mapper.map(bookEntity,Book.class))
               .collect(Collectors.toList());
    }
}
