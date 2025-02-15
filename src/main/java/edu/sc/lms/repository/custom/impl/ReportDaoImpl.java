package edu.sc.lms.repository.custom.impl;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Report;
import edu.sc.lms.entity.ReportEntity;
import edu.sc.lms.repository.custom.ReportDao;
import edu.sc.lms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {
    @Override
    public List<ReportEntity> getWeeklyCirculation() {
        ArrayList<ReportEntity> reportArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT DAYNAME(BorrowedDate) AS DayOfWeek,COUNT(*) AS BorrowedCount FROM bookrecord" +
                    " GROUP BY DayOfWeek ORDER BY FIELD(DayOfWeek,'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')");
            while (rst.next()){
                reportArrayList.add(new ReportEntity(rst.getString(1),
                        rst.getInt(2),
                        null,
                        0,
                        0,
                        0.0));
            }
            return reportArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<ReportEntity> getPopularBookCategoriesByBorrow() {
        ArrayList<ReportEntity> popularBookCategories = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT c.Category, COUNT(br.BookId) AS BorrowCount FROM bookrecord br JOIN book b ON br.BookId = b.BookId JOIN category c ON b.CategoryId = c.CategoryId GROUP BY c.Category ORDER BY BorrowCount DESC");
            while (rst.next()){
                popularBookCategories.add(new ReportEntity(null,
                        0,
                        rst.getString(1),
                        rst.getInt(2),
                        0,
                        0.0));
            }
            return popularBookCategories;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<ReportEntity> getActiveMembers() {
        ArrayList<ReportEntity> activeMemberList = new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(" SELECT DAYNAME(br.BorrowedDate) AS DayofWeek,COUNT(DISTINCT br.MemberId) AS ActiveMember " +
                    "FROM bookrecord br GROUP BY BorrowedDate " +
                    "ORDER BY FIELD(DAYNAME(BR.BorrowedDate),'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')");
            while (rst.next()){
                activeMemberList.add(new ReportEntity(rst.getString(1),
                        0,
                        null,
                        0,
                        rst.getInt(2),
                        0.0));
            }
            return activeMemberList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<ReportEntity> getFineCollection() {
        ArrayList<ReportEntity> fineCollection = new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT DAYNAME(br.ReturnDate) AS weekDay,SUM(f.fine) AS TotalFine FROM " +
                    "fine f JOIN bookrecord br ON f.BookRecord_RecordId = br.recordId " +
                    "WHERE br.ReturnDate IS NOT NULL " +
                    "GROUP BY weekDay " +
                    "ORDER BY FIELD(weekDay,'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')");
            while (rst.next()){
                fineCollection.add(new ReportEntity(rst.getString(1),
                        0,
                        null,
                        0,
                        0,
                        rst.getDouble(2)));
            }
            return fineCollection;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Double getTotalFine() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT SUM(fine) FROM fine");
            return rst.next()? rst.getDouble(1):0.0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Integer getTotalCirculations() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT COUNT(RecordId) FROM Bookrecord");
            return rst.next()? rst.getInt(1):0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean add(Report entity) {
        return false;
    }

    @Override
    public Report search(Report entity) {
        return null;
    }

    @Override
    public boolean update(Report entity) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Report> getAll() {
        return List.of();
    }
}
