package edu.sc.lms.repository.custom.impl;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.BookRecord;
import edu.sc.lms.entity.BookRecordEntity;
import edu.sc.lms.repository.custom.CirculationDao;
import edu.sc.lms.util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CirculationDaoImpl implements CirculationDao {
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
        ArrayList<String> bookTitleList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT BookTitle FROM book");
            while (rst.next()) {
                bookTitleList.add(rst.getString(1));
            }
            return bookTitleList;
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
    public boolean issueBook(BookRecordEntity entity) {
        String fineId = generateFineId();
        String recordId = generaRecordId();

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            ResultSet rst = connection.createStatement().executeQuery("SELECT (SELECT MemberId FROM member WHERE name='" + entity.getMemberName() + "') AS memberId,(SELECT bookId FROM book WHERE BookTitle='" + entity.getBookTitle() + "') ");
            if (rst.next()) {
                PreparedStatement psTm = connection.prepareStatement("INSERT INTO BookRecord VALUES(?,?,?,?,?,?,?)");
                psTm.setString(1, recordId);
                psTm.setString(2, rst.getString(1));
                psTm.setString(3, rst.getString(2));
                psTm.setDate(4, Date.valueOf(LocalDate.now()));
                psTm.setDate(5, Date.valueOf(entity.getReturnDate()));
                psTm.setString(6, null);
                psTm.setInt(7, 0);
                if (psTm.executeUpdate() > 0 && connection.createStatement().executeUpdate("UPDATE Book SET AvailableQty=AvailableQty-1 WHERE BookId='" + rst.getString(2) + "'") > 0) {
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

    @Override
    public List<BookRecordEntity> loadTable() {
        ArrayList<BookRecordEntity> bookRecordArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT b.booktitle AS booktitle," +
                    "m.name AS membername," +
                    "br.BorrowedDate AS BorrowedDate," +
                    "br.ReturnDate AS ReturnDate," +
                    "br.DateGiven AS DateGiven, CASE WHEN br.isreturn = 1 THEN 'returned' ELSE 'not returned' END AS status, " +
                    "f.fine AS FineAmount FROM bookrecord br join member m on br.memberid=m.memberid join book b on br.bookid=b.bookid left join fine f on br.recordid=f.BookRecord_RecordId;");
            while (rst.next()) {
                bookRecordArrayList.add(new BookRecordEntity(null,
                        null,
                        rst.getString(2),
                        null,
                        rst.getString(1),
                        rst.getDate(3).toLocalDate(),
                        rst.getDate(4).toLocalDate(),
                        rst.getString(5),
                        0,
                        rst.getString(6),
                        rst.getDouble(7)
                ));
            }
            return bookRecordArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public BookRecordEntity loadReturnDetails(String memberName, String bookTitle) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT br.BorrowedDate," +
                    "br.ReturnDate," +
                    "f.fine FROM BookRecord " +
                    "br JOIN member m ON br.MemberId=m.MemberId" +
                    " JOIN book b ON br.BookId=b.BookId " +
                    "LEFT JOIN fine f ON br.RecordId=f.BookRecord_RecordId WHERE name='"+memberName+"' AND BookTitle='"+bookTitle+"'");
            return rst.next() ? new BookRecordEntity(null,
                    null,
                    null,
                    null,
                    null,
                    rst.getDate(1).toLocalDate(),
                    rst.getDate(2).toLocalDate(),
                    null,
                    0,
                    null,
                    rst.getDouble(3)) : null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<String> loadBookTitleRe(String memberName) {
        ArrayList<String> bookTitleList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT b.booktitle FROM bookrecord" +
                    " br JOIN member m ON br.memberid=m.memberid" +
                    " JOIN book b ON br.bookid=b.bookid" +
                    " WHERE name='" + memberName + "'");
            while (rst.next()) {
                bookTitleList.add(rst.getString(1));
            }
            return bookTitleList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean calculateFine() {
        ArrayList<BookRecord> localDateArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT RecordId, ReturnDate, isReturn FROM BookRecord");
            while (rst.next()) {
                localDateArrayList.add(new BookRecord(rst.getString(1), null, null, null, null, null, rst.getDate(2).toLocalDate(), null, rst.getInt(3), null, 0.0));
            }
            System.out.println(localDateArrayList.size());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        //Calculating Fine Ony by One
        boolean isUpdated=false;
        int updatedCount=0;
        int chekCount=0;
        for (BookRecord bookRecord : localDateArrayList) {
            long daysBetween = ChronoUnit.DAYS.between(bookRecord.getReturnDate(), LocalDate.now());
            if (daysBetween > 0 && bookRecord.getIsReturn() != 1) {
                chekCount++;
                double calculatedFine = (double) daysBetween * 10;
                try {
                    isUpdated = CrudUtil.execute("UPDATE Fine SET Fine = '" + calculatedFine + "' WHERE  BookRecord_RecordId = '" + bookRecord.getRecordId() + "'");
                    if(isUpdated){
                        updatedCount++;
                    }
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return updatedCount == chekCount;

    }

    @Override
    public Integer borrowedBook() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT COUNT(RecordId) FROM BookRecord");
            return rst.next() ? rst.getInt(1) : 0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Integer returnedBookCount() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT COUNT(IsReturn) FROM bookrecord WHERE IsReturn=1");
            return rst.next() ? rst.getInt(1) : 0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean returnBook(BookRecordEntity bookRecordEntity) {
        Connection connection=null;
        try {
             connection = DBConnection.getInstance().getConnection();
             connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ResultSet rst = connection.createStatement().executeQuery("select br.recordid from bookrecord br " +
                    "join member m on br.memberid=m.memberid " +
                    "join book b on br.bookid=b.bookid where name='"+bookRecordEntity.getMemberName()+"' and booktitle='"+bookRecordEntity.getBookTitle()+"'");
            String recordId = rst.next() ? rst.getString(1) : null;
            if (recordId != null) {
                if(connection.createStatement().executeUpdate("UPDATE bookRecord SET DateGiven='" + bookRecordEntity.getDateGiven() + "', " +
                        "IsReturn='" + bookRecordEntity.getIsReturn() + "' WHERE recordId='" + recordId + "'")>0 && connection.createStatement().executeUpdate("UPDATE Fine SET fine=fine-'"+bookRecordEntity.getFineAmount()+"' " +
                        "WHERE BookRecord_RecordId='"+recordId+"'")>0){
                    connection.commit();
                    return true;
                }
                connection.rollback();
                return false;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                assert connection!=null;
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new IllegalArgumentException(e);
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean add(BookRecordEntity entity) {
        return false;
    }

    @Override
    public BookRecordEntity search(BookRecordEntity entity) {
        return null;
    }

    @Override
    public boolean update(BookRecordEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<BookRecordEntity> getAll() {
        return List.of();
    }
}
