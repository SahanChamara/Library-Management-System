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
}
