package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Report;
import edu.sc.lms.service.SuperService;

import java.util.List;

public interface ReportService extends SuperService {
    List<Report> getWeeklyCirculation();
    List<Report> getPopularBookCategoriesByBorrow();
    List<Report> getActiveMembers();
    List<Report> getFineCollection();
}
