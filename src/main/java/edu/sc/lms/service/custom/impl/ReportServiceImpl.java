package edu.sc.lms.service.custom.impl;

import edu.sc.lms.dto.Report;
import edu.sc.lms.repository.DaoFactory;
import edu.sc.lms.repository.custom.ReportDao;
import edu.sc.lms.service.custom.ReportService;
import edu.sc.lms.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    ReportDao reportDao = DaoFactory.getInstance().getDaoType(DaoType.REPORT);
    ModelMapper mapper = new ModelMapper();

    @Override
    public List<Report> getWeeklyCirculation() {
        return reportDao.getWeeklyCirculation()
                .stream()
                .map(reportEntity -> mapper.map(reportEntity, Report.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> getPopularBookCategoriesByBorrow() {
        return reportDao.getPopularBookCategoriesByBorrow()
                .stream()
                .map(popularCategoriesByBorrow -> mapper.map(popularCategoriesByBorrow, Report.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> getActiveMembers() {
        return reportDao.getActiveMembers()
                .stream()
                .map(activeMembers ->mapper.map(activeMembers, Report.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> getFineCollection() {
        return reportDao.getFineCollection()
                .stream()
                .map(fineCollection ->mapper.map(fineCollection, Report.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getTotalFine() {
        return "RS."+reportDao.getTotalFine();
    }

    @Override
    public Integer getTotalCirculations() {
        return reportDao.getTotalCirculations();
    }
}
