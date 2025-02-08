package edu.sc.lms.service.custom.impl;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.Book;
import edu.sc.lms.service.custom.DashBoardService;
import javafx.scene.image.Image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardServiceImpl implements DashBoardService {
    private Image image;

    /*private static DashboardServiceImpl instance;
    private DashboardServiceImpl() {
    }
    public static DashboardServiceImpl getInstance() {
        return instance != null ? instance : new DashboardServiceImpl();
    }*/

    @Override
    public String totalBooks() {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT COUNT(BookId) FROM book");
            return rst.next() ? rst.getString(1) : null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String activeMembers() {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT COUNT(MemberId) FROM member");
            return rst.next() ? rst.getString(1) : null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String borrowedBooks() {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT COUNT(RecordId) FROM bookrecord");
            return rst.next() ? rst.getString(1) : null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Book> loadBookToCard() {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT BookTitle,price,coverImg FROM book");
            while (rst.next()) {
                bookArrayList.add(new Book(null, rst.getString(1), null, rst.getDouble(2), null, rst.getString(3), null, null,null,null,null,null));
            }
            return bookArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
