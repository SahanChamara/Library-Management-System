package edu.sc.lms.controller.dashboard;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.Book;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardController implements DashBoardService {
    private static DashboardController instance;
    private Image image;

    private DashboardController() {
    }

    public static DashboardController getInstance() {
        return instance != null ? instance : new DashboardController();
    }

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
                bookArrayList.add(new Book(null, rst.getString(1), null, rst.getDouble(2), null, rst.getString(3), null, null));
            }
            return bookArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
