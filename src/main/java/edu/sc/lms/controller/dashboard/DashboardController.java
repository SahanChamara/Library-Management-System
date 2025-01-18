package edu.sc.lms.controller.dashboard;

import edu.sc.lms.dbconnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardController implements DashBoardService {
    private static DashboardController instance;

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
            return rst.next()?rst.getString(1):null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
