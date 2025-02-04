package edu.sc.lms.controller.circulation;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.BookRecord;
import edu.sc.lms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean issueBook(BookRecord bookRecord) {
        String membername = bookRecord.getMemberName();
        String booktitle = bookRecord.getBookTitle();
        String recordId = generaRecordId();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT (SELECT MemberId FROM member WHERE name='" + bookRecord.getMemberName() + "') AS memberId,(SELECT bookId FROM book WHERE BookTitle='" + bookRecord.getBookTitle() + "') ");
            if (rst.next()) {
                String memberId = rst.getString(1);
                String bookId = rst.getString(2);
                return true;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return false;
    }
}
