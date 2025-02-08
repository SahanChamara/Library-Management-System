package edu.sc.lms.repository.custom.impl;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Book;
import edu.sc.lms.entity.BookEntity;
import edu.sc.lms.repository.custom.DashboardDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDaoImpl implements DashboardDao {
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
    public List<BookEntity> loadBookToCard() {
        ArrayList<BookEntity> bookArrayList = new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT BookTitle,price,coverImg FROM book");
            while (rst.next()) {
                bookArrayList.add(new BookEntity(null, rst.getString(1), null, rst.getDouble(2), null, rst.getString(3), null, null, null, null, null, null));
            }
            return bookArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean add(BookEntity entity) {
        return false;
    }

    @Override
    public BookEntity search(BookEntity entity) {
        return null;
    }

    @Override
    public boolean update(BookEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<BookEntity> getAll() {
        return List.of();
    }
}
