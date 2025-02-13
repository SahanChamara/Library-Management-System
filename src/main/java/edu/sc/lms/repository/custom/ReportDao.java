package edu.sc.lms.repository.custom;

import edu.sc.lms.dto.Report;
import edu.sc.lms.entity.ReportEntity;
import edu.sc.lms.repository.CrudDao;

import java.util.List;

public interface ReportDao extends CrudDao<Report,Integer> {
    List<ReportEntity> getWeeklyCirculation();
}
