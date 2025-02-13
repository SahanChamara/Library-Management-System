package edu.sc.lms.repository.custom.impl;

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
                reportArrayList.add(new ReportEntity(rst.getString(1),rst.getInt(2)));
            }
            return reportArrayList;
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
