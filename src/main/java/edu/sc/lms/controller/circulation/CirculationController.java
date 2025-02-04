package edu.sc.lms.controller.circulation;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.BookRecord;
import edu.sc.lms.util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CirculationController implements CirculationService {
    private static CirculationController instance;

    private CirculationController() {
    }

    public static CirculationController getInstance() {
        return instance == null ? instance = new CirculationController() : instance;
    }

    @Override
    public List<String> loadMemberNames() {
        ArrayList<String> memberArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT Name FROM member");
            while (rst.next()) {
                memberArrayList.add(rst.getString(1));
            }
            return memberArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<String> loadBookTitle() {
        ArrayList<String> booktitleList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT BookTitle FROM book");
            while (rst.next()) {
                booktitleList.add(rst.getString(1));
            }
            return booktitleList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generaRecordId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT RecordId FROM BookRecord ORDER BY RecordId DESC LIMIT 1");
            if (rst.next()) {
                return String.format("R%03d", Integer.parseInt(rst.getString(1).substring(1)) + 1);
            }
            return "R001";
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String generateFineId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT FineId FROM fine ORDER BY fineId DESC LIMIT 1");
            if (rst.next()) {
                return String.format("F%03d", Integer.parseInt(rst.getString(1).substring(1)) + 1);
            }
            return "F001";
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean issueBook(BookRecord bookRecord) {
        String fineId = generateFineId();
        String recordId = generaRecordId();

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            ResultSet rst = connection.createStatement().executeQuery("SELECT (SELECT MemberId FROM member WHERE name='" + bookRecord.getMemberName() + "') AS memberId,(SELECT bookId FROM book WHERE BookTitle='" + bookRecord.getBookTitle() + "') ");
            if (rst.next()) {
                PreparedStatement psTm = connection.prepareStatement("INSERT INTO BookRecord VALUES(?,?,?,?,?,?,?)");
                psTm.setString(1, recordId);
                psTm.setString(2, rst.getString(1));
                psTm.setString(3, rst.getString(2));
                psTm.setDate(4, Date.valueOf(LocalDate.now()));
                psTm.setDate(5, Date.valueOf(bookRecord.getReturnDate()));
                psTm.setString(6, null);
                psTm.setInt(7, 0);
                if (psTm.executeUpdate() > 0 && connection.createStatement().executeUpdate("UPDATE Book SET= AvailableQty=AvailableQty-1 WHERE BookId='" + rst.getString(2) + "'") > 0) {
                    PreparedStatement psTm2 = connection.prepareStatement("INSERT INTO Fine VALUES(?,?,?)");
                    psTm2.setString(1, fineId);
                    psTm2.setDouble(2, 0.00);
                    psTm2.setString(3, recordId);
                    if (psTm2.executeUpdate() > 0) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                return false;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
